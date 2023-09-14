package ru.practicum.stats.server.stats.service;

import ru.practicum.stats.dto.model.StatsDto;

import java.util.List;

public interface StatsService {
    List<StatsDto> getStats(String start, String end, List<String> uris, Boolean unique);
}
