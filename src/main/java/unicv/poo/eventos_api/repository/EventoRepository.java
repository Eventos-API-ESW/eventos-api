package unicv.poo.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicv.poo.eventos_api.entity.Evento;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    boolean existsByLocalIdAndCapacidadeGreaterThan(Long localId, int capacidade);
    List<Evento> findByLocalId(Long localId);
}


