package ru.practicum.stats.server.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.model.StatsDto;
import ru.practicum.stats.server.exception.ValidationDateException;
import ru.practicum.stats.server.storage.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatsServerImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StatsDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(URLDecoder.decode(start, Charset.defaultCharset()));
        LocalDateTime endTime = LocalDateTime.parse(URLDecoder.decode(end, Charset.defaultCharset()));
        validDate(startTime, endTime);
        if (unique) {
            return statsRepository.findUniqueStats(startTime, endTime, uris);
        } else {
            return statsRepository.findStats(startTime, endTime, uris);
        }
    }

    private void validDate(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start) || start.isAfter(end)) {
            throw new ValidationDateException("Невенно заданы даты для поиска");
        }
    }
}