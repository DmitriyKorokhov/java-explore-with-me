package ru.practicum.explorewithme.stats.server.stats.service;

import ru.practicum.explorewithme.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
