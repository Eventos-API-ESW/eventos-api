package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public record LocalRequestDTO(
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O endereco é obrigatório")
    private String endereco;

    @NotNull(message = "A capacidade é obrigatório")
    @Min(value = 1 , message = "A capacidade deve ser no minimo 1")
    private Integer capacidade;
)
{}