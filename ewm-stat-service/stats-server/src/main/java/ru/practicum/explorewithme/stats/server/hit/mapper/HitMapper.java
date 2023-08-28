package ru.practicum.explorewithme.stats.server.hit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.formatter.DateFormatter;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface HitMapper {

    HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    @Mapping(target = "timestamp", expression = "java(convertToLocalDateTime(hitDto.getTimestamp()))")
    Hit toHit(HitDto hitDto);

    default LocalDateTime convertToLocalDateTime(String time) {
        return DateFormatter.formatDate(time);
    }
}
