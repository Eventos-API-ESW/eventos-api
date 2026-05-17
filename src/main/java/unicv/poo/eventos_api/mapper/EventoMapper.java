package unicv.poo.eventos_api.mapper;

import org.mapstruct.Mapper;
import unicv.poo.eventos_api.dto.EventoRequestDto;
import unicv.poo.eventos_api.dto.EventoResponseDto;
import unicv.poo.eventos_api.entity.Evento;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    EventoResponseDto toResponseDto(Evento evento);

    Evento toEntity(EventoRequestDto eventoRequestDto);

    List<EventoResponseDto> toResponseDtoList(List<Evento> eventoList);

}