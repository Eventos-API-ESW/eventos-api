package unicv.poo.eventos_api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unicv.poo.eventos_api.dto.ParticipanteDTO;
import unicv.poo.eventos_api.service.ParticipanteService;
import unicv.poo.eventos_api.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Participantes", description = "Endpoints para gerenciamento de participantes.")
@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @Operation(summary = "Listar todos", description = "Retorna lista de todos os participantes cadastrados.")
    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> listarParticipantes() {
        List<ParticipanteDTO> participantes = participanteService.listarTodos();
        return ResponseEntity.ok(participantes);
    }

    @Operation(summary = "Buscar por ID", description = "Retorna um participante específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> buscarPorId(@PathVariable Long id) {
        ParticipanteDTO participanteDTO = participanteService.buscarPorId(id);
        return ResponseEntity.ok(participanteDTO);
    }

    @Operation(summary = "Criar novo", description = "Cria um novo participante com validações.")
    @PostMapping
    public ResponseEntity<unicv.poo.eventos_api.service.Utils.ApiResponse<ParticipanteDTO>> criar(
            @Valid @RequestBody ParticipanteDTO participanteDTO) {
        try {
            ParticipanteDTO salvo = participanteService.salvar(participanteDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new unicv.poo.eventos_api.service.Utils.ApiResponse<>(salvo));
        } catch (RuntimeException e) {
            ErrorResponse error = new ErrorResponse("ERRO", e.getMessage());
            return ResponseEntity.badRequest().body(new unicv.poo.eventos_api.service.Utils.ApiResponse<>(error));
        }
    }

    @Operation(summary = "Deletar", description = "Deleta um participante pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        participanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}






//ALTERNATIVA


// package unicv.poo.eventos_api.controller;

// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import unicv.poo.eventos_api.dto.ParticipanteDTO;
// import unicv.poo.eventos_api.service.ParticipanteService;
// import unicv.poo.eventos_api.service.Utils.ApiResponse;
// import unicv.poo.eventos_api.service.Utils.ErrorResponse;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;

// @Tag(name = "Participantes", description = "Endpoints para gerenciamento de participantes.")
// @RestController
// @RequestMapping("/api/participantes")
// public class ParticipanteController {

//     @Autowired
//     private ParticipanteService participanteService;

//     @Operation(summary = "Listar todos", description = "Retorna lista de todos os participantes cadastrados.")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Sucesso"),
//         @ApiResponse(responseCode = "500", description = "Erro interno")
//     })
//     @GetMapping
//     public ResponseEntity<List<ParticipanteDTO>> listarParticipantes() {
//         List<ParticipanteDTO> participantes = participanteService.listarTodos();
//         return ResponseEntity.ok(participantes);
//     }

//     @Operation(summary = "Buscar por ID", description = "Retorna um participante específico pelo ID.")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Participante encontrado"),
//         @ApiResponse(responseCode = "404", description = "Participante não encontrado")
//     })
//     @GetMapping("/{id}")
//     public ResponseEntity<ParticipanteDTO> buscarPorId(@PathVariable Long id) {
//         ParticipanteDTO participanteDTO = participanteService.buscarPorId(id);
//         return ResponseEntity.ok(participanteDTO);
//     }

//     @Operation(summary = "Criar novo", description = "Cria um novo participante com validações.")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Participante criado"),
//         @ApiResponse(responseCode = "400", description = "Dados inválidos")
//     })
//     @PostMapping
//     public ResponseEntity<unicv.poo.eventos_api.service.Utils.ApiResponse<ParticipanteDTO>> criar(@Valid @RequestBody ParticipanteDTO participanteDTO) {
//         try {
//             ParticipanteDTO salvo = participanteService.salvar(participanteDTO);
//             return ResponseEntity.status(HttpStatus.CREATED).body(new unicv.poo.eventos_api.service.Utils.ApiResponse<>(salvo));
//         } catch (RuntimeException e) {
//             ErrorResponse error = new ErrorResponse("ERRO", e.getMessage());
//             return ResponseEntity.badRequest().body(new unicv.poo.eventos_api.service.Utils.ApiResponse<>(error));
//         }
//     }

//     @Operation(summary = "Deletar", description = "Deleta um participante pelo ID.")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
//         @ApiResponse(responseCode = "404", description = "Participante não encontrado")
//     })
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deletar(@PathVariable Long id) {
//         participanteService.deletar(id);
//         return ResponseEntity.noContent().build();
//     }
// }