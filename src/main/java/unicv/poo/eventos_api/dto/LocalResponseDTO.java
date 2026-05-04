package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


/* mudanca dia 02/05/2026 */


public record LocalResponseDTO(
    String nome, 
    String endereco,
    Integer capacidade

    )
{}

