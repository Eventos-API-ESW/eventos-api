package unicv.poo.eventos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicv.poo.eventos_api.entity.Ingresso;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
}
