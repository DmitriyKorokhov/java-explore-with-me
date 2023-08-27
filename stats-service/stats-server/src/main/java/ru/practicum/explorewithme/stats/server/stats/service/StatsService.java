package ru.practicum.explorewithme.stats.server.stats.service;

import ru.practicum.explorewithme.stats.dto.StatsDto;

import java.util.List;

public interface StatsService {
    List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique);
}
