package ru.practicum.explorewithme.stats.server.hit.service;

import ru.practicum.explorewithme.stats.dto.HitDto;

public interface HitService {
    void addHit(HitDto hitDto);
}