package unicv.poo.eventos_api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicv.poo.eventos_api.dto.ParticipanteDTO;
import unicv.poo.eventos_api.entities.Participante;
import unicv.poo.eventos_api.mapper.ParticipanteMapper;
import unicv.poo.eventos_api.repository.IParticipanteRepository;

@Service
public class ParticipanteService {

    @Autowired
    private IParticipanteRepository participanteRepository;

    @Autowired
    private ParticipanteMapper participanteMapper;

    public List<ParticipanteDTO> listarTodos() {
        return participanteMapper.toDTOList(participanteRepository.findAll());
    }

    public ParticipanteDTO buscarPorId(Long id) {
        return participanteRepository.findById(id)
                .map(participanteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
    }

    public ParticipanteDTO salvar(ParticipanteDTO participanteDTO) {
        if (participanteRepository.existsByEmail(participanteDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        Participante participante = participanteMapper.toEntity(participanteDTO);
        return participanteMapper.toDTO(participanteRepository.save(participante));
    }

    public void deletar(Long id) {
        participanteRepository.deleteById(id);
    }
}

//Aparentemente tudo ok