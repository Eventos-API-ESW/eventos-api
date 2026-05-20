package unicv.poo.eventos_api.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // NOVO - garante transação atômica
import unicv.poo.eventos_api.dto.ParticipanteRequestDto;
import unicv.poo.eventos_api.dto.ParticipanteResponseDto;
import unicv.poo.eventos_api.entity.Participante;
import unicv.poo.eventos_api.entity.Inscricao; // NOVO - importa entidade Inscrição
import unicv.poo.eventos_api.mapper.ParticipanteMapper;
import unicv.poo.eventos_api.repository.ParticipanteRepository;
import unicv.poo.eventos_api.repository.InscricaoRepository; // NOVO - importa repositório de Inscrição

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired // NOVO - injeta repositório de Inscrição para manipular inscrições
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private ParticipanteMapper participanteMapper;

    public List<ParticipanteResponseDto> listarTodos() {
        return participanteMapper.toDTOList(participanteRepository.findAll());
    }

    public ParticipanteResponseDto buscarPorId(Long id) {
        return participanteRepository.findById(id)
                .map(participanteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
    }

    public ParticipanteResponseDto salvar(ParticipanteRequestDto participanteDTO) {

        if (participanteRepository.existsByNome(participanteDTO.nome())) {
            throw new RuntimeException("Nome já cadastrado");
        }

        if (participanteRepository.existsByEmail(participanteDTO.email())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        if (participanteRepository.existsByTelefone(participanteDTO.telefone())) {
            throw new RuntimeException("Telefone já cadastrado");
        }
        Participante participante = participanteMapper.toEntity(participanteDTO);
        return participanteMapper.toDTO(participanteRepository.save(participante));
    }

    public ParticipanteResponseDto atualizar(Long id, ParticipanteRequestDto participanteDTO) {
        // Busca o participante existente
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        
        // Valida nome duplicado (exceto se for o mesmo nome)
        if (!participante.getNome().equals(participanteDTO.nome()) &&
                participanteRepository.existsByNome(participanteDTO.nome())) {
            throw new RuntimeException("Nome já cadastrado");
        }
        
        // Valida email duplicado (exceto se for o mesmo email)
        if (!participante.getEmail().equals(participanteDTO.email()) &&
                participanteRepository.existsByEmail(participanteDTO.email())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        // Valida telefone duplicado (exceto se for o mesmo telefone)
        if (!participante.getTelefone().equals(participanteDTO.telefone()) &&
                participanteRepository.existsByTelefone(participanteDTO.telefone())) {
            throw new RuntimeException("Telefone já cadastrado");
        }
        
        // Atualiza os dados
        participante.setNome(participanteDTO.nome());
        participante.setEmail(participanteDTO.email());
        participante.setTelefone(participanteDTO.telefone());
        
        // Salva e retorna DTO
        return participanteMapper.toDTO(participanteRepository.save(participante));
    }

    @Transactional // NOVO - anotação que garante que toda operação é atômica (tudo executa ou nada executa)
    public void deletar(Long id) {
        // MODIFICADO - validação mantida
        if (!participanteRepository.existsById(id)) {
            throw new RuntimeException("Participante não encontrado");
        }

        // NOVO - busca todas as inscrições associadas ao participante usando o novo método do repositório
        List<Inscricao> inscricoes = inscricaoRepository.findByParticipanteId(id);

        // NOVO - percorre todas as inscrições encontradas
        for (Inscricao inscricao : inscricoes) {
            // NOVO - marca o status da inscrição como "CANCELADA" ao invés de deletar
            inscricao.setStatus("CANCELADA");
            // NOVO - salva a inscrição com status atualizado no banco de dados
            inscricaoRepository.save(inscricao);
        }

        // MODIFICADO - apenas após cancelar todas as inscrições, deleta o participante
        participanteRepository.deleteById(id);
    }
}