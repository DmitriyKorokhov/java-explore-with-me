package ru.practicum.explorewithme.stats.server.hit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.server.hit.storage.HitRepository;
import ru.practicum.explorewithme.stats.server.hit.mapper.HitMapper;
import ru.practicum.explorewithme.stats.server.hit.model.Hit;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    public void addHit(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);
        hitRepository.save(hit);
    }
}
