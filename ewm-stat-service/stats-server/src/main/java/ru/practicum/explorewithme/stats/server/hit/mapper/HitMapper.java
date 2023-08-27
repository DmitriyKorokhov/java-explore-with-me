package ru.practicum.explorewithme.stats.server.hit.mapper;

import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.formatter.DateFormatter;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

public class HitMapper {
    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .ip(hitDto.getIp())
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .timestamp(DateFormatter.formatDate(hitDto.getTimestamp()))
                .build();
    }
}