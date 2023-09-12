package ru.practicum.stats.server.hit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.stats.dto.model.HitDto;
import ru.practicum.stats.server.hit.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {

    HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    Hit toHit(HitDto hitDto);
}