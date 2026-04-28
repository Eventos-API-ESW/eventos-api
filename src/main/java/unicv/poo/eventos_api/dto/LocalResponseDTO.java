package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public record LocalResponseDTO(
    String nome, 
    String endereco,
    Integer capacidade

    )
{}

