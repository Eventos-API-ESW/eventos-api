package unicv.poo.eventos_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ParticipanteDTO {

    private Long id;

    @Size(min = 1, max = 100, message = "O nome deve conter entre 1 e 100 caracteres")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Size(min = 2, max = 100, message = "O email deve conter entre 2 e 100 caracteres")
    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @Size(min = 8, max = 15, message = "O telefone deve conter entre 8 e 15 caracteres")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

}
