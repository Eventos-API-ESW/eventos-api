package unicv.poo.eventos_api.dto;

import java.math.BigDecimal;

public record IngressoResponseDto(
        Long id,
        Long eventoId,
        String tipo,
        BigDecimal preco,
        Integer quantidade
) {
}
