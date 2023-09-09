package ru.practicum.stats.server.hit.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.stats.dto.model.HitDto;
import ru.practicum.stats.server.hit.model.Hit;

@UtilityClass
public class HitMapper {
    public Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .uri(hitDto.getUri())
                .app(hitDto.getApp())
                .ip(hitDto.getIp())
                .timestamp(hitDto.getTimestamp())
                .build();
    }
}