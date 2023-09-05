package ru.practicum.explorewithme.stats.server.hit.service;

import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

public interface HitService {
    Hit addHit(HitDto hitDto);
}