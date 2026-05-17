package unicv.poo.eventos_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import unicv.poo.eventos_api.entity.Inscricao;
import unicv.poo.eventos_api.repository.InscricaoRepository;

@Service
public class InscricaoService {

    private final InscricaoRepository repository;

    public InscricaoService(InscricaoRepository repository) {
        this.repository = repository;
    }

    public List<Inscricao> listarTodas() {
        return repository.findAll();
    }

    public Inscricao realizarInscricao(Inscricao inscricao) {

        LocalDate hoje = LocalDate.now();

        if (inscricao.getEvento().getDataEvento().isBefore(hoje)) {
            throw new RuntimeException("Não é possível se inscrever em um evento que já ocorreu.");
        }

        int capacidadeMaxima = inscricao.getEvento().getLocal().getCapacidade();
        Long eventoId = inscricao.getEvento().getId();

        long totalInscritos = repository.countByEventoIdAndStatus(eventoId, "CONFIRMADA");

        if (totalInscritos >= capacidadeMaxima) {
            throw new RuntimeException("Desculpe, esse evento já atingiu a capacidade máxima de "
                    + capacidadeMaxima + " vagas.");
        }

        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CONFIRMADA");

        return repository.save(inscricao);
    }
}