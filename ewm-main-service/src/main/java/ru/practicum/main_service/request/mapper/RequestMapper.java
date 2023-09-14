package ru.practicum.main_service.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.main_service.request.model.Request;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    @Mapping(target = "requester", expression = "java(request.getRequester().getId())")
    @Mapping(target = "event", expression = "java(request.getEvent().getId())")
    @Mapping(target = "status", expression = "java(request.getStatus().name())")
    ParticipationRequestDto toParticipationRequestDto(Request request);
}