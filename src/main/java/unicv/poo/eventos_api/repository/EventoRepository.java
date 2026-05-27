package unicv.poo.eventos_api.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import unicv.poo.eventos_api.entity.Evento; // Ajustado para Evento
import unicv.poo.eventos_api.enums.StatusEventoEnum;




@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Ajustado para 'LocalId' e 'Capacidade', combinando com a sua Entity Evento
    boolean existsByLocalIdAndCapacidadeGreaterThan(Long localId, int capacidade);

    boolean existsByLocalIdAndDataAfter(Long localId, LocalDateTime data);
    boolean existsByLocalIdAndStatusNot(Long localId, StatusEventoEnum status);    
}


