package unicv.poo.eventos_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(name = "local_id", nullable = false)
    private Long localId;
}
