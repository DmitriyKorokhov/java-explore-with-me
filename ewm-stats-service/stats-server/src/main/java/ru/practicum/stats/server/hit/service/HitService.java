package ru.practicum.stats.server.hit.service;

import ru.practicum.stats.dto.model.HitDto;
import ru.practicum.stats.server.hit.model.Hit;

public interface HitService {
    Hit addHit(HitDto endpointHit);
}