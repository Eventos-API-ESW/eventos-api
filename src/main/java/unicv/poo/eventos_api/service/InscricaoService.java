package unicv.poo.eventos_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import unicv.poo.eventos_api.entity.Evento;
import unicv.poo.eventos_api.entity.Inscricao;
import unicv.poo.eventos_api.entity.Participante;
import unicv.poo.eventos_api.exception.RegraNegocioException;
import unicv.poo.eventos_api.repository.InscricaoRepository;
import unicv.poo.eventos_api.repository.EventoRepository;
import unicv.poo.eventos_api.repository.ParticipanteRepository;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoService eventoService;
    private final ParticipanteService participanteService;

    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }

    public Inscricao buscarPorId(long id) {
        return inscricaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Inscrição não encontrada."));
    }

    public List<Inscricao> listarPorEvento(Long eventoId) {
        return inscricaoRepository.findByEventoId(eventoId);
    }

    @Transactional
    public Inscricao realizarInscricao(Inscricao inscricao) {
        if (inscricao == null || inscricao.getEvento() == null || inscricao.getParticipante() == null) {
            throw new RegraNegocioException("Dados da inscrição invalidos.");
        }

        Long eventoId = inscricao.getEvento().getId();
        Long participanteId = inscricao.getParticipante().getId();

        if (eventoId == null || participanteId == null) {
            throw new RegraNegocioException("O ID do evento e do participante não podem ser nulos.");
        }

        Evento evento = eventoService.buscarEntidadePorId(eventoId);

        if (evento.getDataEvento() != null && LocalDate.now().isAfter(evento.getDataEvento())) {
            throw new RegraNegocioException("Não é possível se inscrever em um evento que já iniciou ou ocorreu.");
        }

        boolean jaInscrito = inscricaoRepository.existsByParticipanteIdAndEventoIdAndStatus(participanteId, eventoId,
                "CONFIRMADA");
        if (jaInscrito) {
            throw new RegraNegocioException("Este participante já está inscrito neste evento.");
        }

        int capacidadeMaxima = evento.getLocal().getCapacidade();
        long totalInscritos = inscricaoRepository.countByEventoIdAndStatus(eventoId, "CONFIRMADA");

        if (totalInscritos >= capacidadeMaxima) {
            throw new RegraNegocioException("Desculpe, esse evento já atingiu a capacidade máxima de "
                    + capacidadeMaxima + " vagas.");
        }

        Participante participante = participanteService.buscarEntidadePorId(participanteId);

        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CONFIRMADA");

        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public Inscricao cancelarInscricao(Long id) {
        if (id == null) {
            throw new RegraNegocioException("O ID da inscrição não pode ser nulo.");
        }

        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscrição não encontrada."));

        LocalDate hoje = LocalDate.now();

        if (inscricao.getEvento().getDataEvento() != null && inscricao.getEvento().getDataEvento().isBefore(hoje)) {
            throw new RegraNegocioException("Não é possível cancelar a inscrição de um evento que já ocorreu.");
        }

        inscricao.setStatus("CANCELADA");
        return inscricaoRepository.save(inscricao);
    }
}