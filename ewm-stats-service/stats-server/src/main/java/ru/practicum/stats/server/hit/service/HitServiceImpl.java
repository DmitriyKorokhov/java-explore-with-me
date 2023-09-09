package ru.practicum.stats.server.hit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.model.HitDto;
import ru.practicum.stats.server.hit.mapper.HitMapper;
import ru.practicum.stats.server.hit.model.Hit;
import ru.practicum.stats.server.storage.StatsRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HitServiceImpl implements HitService {

    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public Hit addHit(HitDto hitDto) {
        return statsRepository.save(HitMapper.toHit(hitDto));
    }
}
