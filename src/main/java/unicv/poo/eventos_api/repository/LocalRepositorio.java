package unicv.poo.eventos_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unicv.poo.eventos_api.Entities.Local;

public interface LocalRepository extends JpaRepository<Local, Long> {
}