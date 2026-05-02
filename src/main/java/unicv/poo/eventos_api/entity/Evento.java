package unicv.poo.eventos_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @NotNull
    @FutureOrPresent
    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;

    @NotNull
    @Column(nullable = false)
    private LocalTime horario;

    @Positive
    @Column(nullable = false)
    private Integer capacidade;

    @NotNull
    @Column(name = "local_id", nullable = false)
    private Long localId;
}
