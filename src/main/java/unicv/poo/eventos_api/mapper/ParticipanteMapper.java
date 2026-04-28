package unicv.poo.eventos_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import unicv.poo.eventos_api.dto.ParticipanteDTO;
import unicv.poo.eventos_api.entities.Participante;

@Mapper(componentModel = "spring")
public interface ParticipanteMapper {

    ParticipanteDTO toDTO(Participante participante);

    Participante toEntity(ParticipanteDTO participanteDTO);

    List<ParticipanteDTO> toDTOList(List<Participante> participantes);

}
