package unicv.poo.eventos_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import unicv.poo.eventos_api.dto.EventoRequestDto;
import unicv.poo.eventos_api.dto.EventoResponseDto;
import unicv.poo.eventos_api.service.EventoService;

import java.util.List;

@Tag(name = "Eventos", description = "Endpoints para gerenciamento de eventos.")
@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @Operation(summary = "Criar um evento", description = "Cadastra um evento com status ABERTO definido automaticamente.")
    @PostMapping
    public ResponseEntity<EventoResponseDto> criar(@RequestBody @Valid EventoRequestDto requestDto) {
        EventoResponseDto responseDto = eventoService.salvar(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Listar eventos", description = "Retorna todos os eventos cadastrados.")
    @GetMapping
    public ResponseEntity<List<EventoResponseDto>> listar() {
        List<EventoResponseDto> responseDtos = eventoService.listarTodos();
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "Buscar evento por ID", description = "Retorna os detalhes de um evento pelo identificador.")
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDto> buscarPorId(@PathVariable Long id) {
        EventoResponseDto responseDto = eventoService.buscarPorId(id);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Atualizar um evento", description = "Atualiza os dados do evento. A capacidade não pode ser menor que a soma dos ingressos.")
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EventoRequestDto requestDto
    ) {
        EventoResponseDto responseDto = eventoService.atualizar(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Cancelar um evento", description = "Altera o status do evento para CANCELADO sem remover o registro.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
