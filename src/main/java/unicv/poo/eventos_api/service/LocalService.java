package unicv.poo.eventos_api.service;

import lombok.RequiredArgsConstructor;
import unicv.poo.eventos_api.entity.Local;
import unicv.poo.eventos_api.exception.RegraNegocioException;
import unicv.poo.eventos_api.dto.LocalRequestDTO;
import unicv.poo.eventos_api.dto.LocalResponseDTO;
import unicv.poo.eventos_api.mapper.LocalMapper;
import unicv.poo.eventos_api.repository.EventoRepository;
import unicv.poo.eventos_api.repository.LocalRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;
    private final EventoRepository eventoRepository;
    private final EventoService eventoService;
    private final LocalMapper localMapper;

    public List<LocalResponseDTO> listarTodos(){
        List<Local> locais = localRepository.findAll();
        return localMapper.toResponseDTOList(locais);
    }

    public LocalResponseDTO buscarPorId(@NonNull Long id){
        Local local = localRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Local não encontrado com o ID: " + id));
        return localMapper.toResponseDTO(local);
    }

    public LocalResponseDTO salvar(LocalRequestDTO localRequestDTO){
        Local local = localMapper.toEntity(localRequestDTO);
        Local localSalvo = localRepository.save(local);
        return localMapper.toResponseDTO(localSalvo);
    }

    public LocalResponseDTO atualizar(Long id, LocalRequestDTO localRequestDTO){
        Local localExistente = localRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Local não encontrado."));

        if(localRequestDTO.capacidade()< localExistente.getCapacidade()){
        boolean possuiEventoMaior = eventoRepository.existsByLocalIdAndCapacidadeGreaterThan(id, localRequestDTO.capacidade());
            if(possuiEventoMaior){
                throw new RegraNegocioException("Existe um evento previsto que excede a nova capacidade.");
            }
        }

        Local local = localMapper.toEntity(localRequestDTO);
        local.setId(id);

        Local localAtualizado = localRepository.save(local);
        return localMapper.toResponseDTO(localAtualizado);
    }
    

    public void deletar(@NonNull Long id){
        if(!localRepository.existsById(id)){
            throw new EntityNotFoundException("Local não encontrado.");
        }

        if (eventoService.existeEventoNoLocal(id)) {
            throw new RegraNegocioException("Existem eventos cadastrados para este local.");
        }

        localRepository.deleteById(id);
    }
}