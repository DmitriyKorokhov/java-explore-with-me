package ru.practicum.events.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.events.request.dto.ParticipationRequestDto;
import ru.practicum.events.request.model.Request;

@UtilityClass
public class RequestMapper {
    public ParticipationRequestDto requestToParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().name())
                .build();
    }
}