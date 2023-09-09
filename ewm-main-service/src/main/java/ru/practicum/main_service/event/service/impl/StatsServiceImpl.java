package ru.practicum.main_service.event.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.client.StatsClient;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.service.StatsService;
import ru.practicum.main_service.request.storage.RequestRepository;
import ru.practicum.main_service.exception.ValidationException;
import ru.practicum.stats.dto.model.HitDto;
import ru.practicum.stats.dto.model.StatsDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final RequestRepository requestRepository;
    private final StatsClient statsClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value(value = "${app.name}")
    private String appName;

    @Override
    public void addHit(HttpServletRequest request) {
        HitDto endpointHit = HitDto.builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        statsClient.postHit(endpointHit);
    }

    @Override
    public Map<Long, Long> getViews(List<Event> events) {
        Map<Long, Long> views = new HashMap<>();
        List<Event> publishedEvents = getPublished(events);
        if (events.isEmpty()) {
            return views;
        }
        Optional<LocalDateTime> minPublishedOn = getMinPublishedOn(publishedEvents);
        if (minPublishedOn.isPresent()) {
            LocalDateTime start = minPublishedOn.get();
            LocalDateTime end = LocalDateTime.now();
            List<String> uris = getURIs(publishedEvents);
            List<StatsDto> stats = getStats(start, end, uris, true);
            putStats(views, stats);
        }
        return views;
    }

    @Override
    public Map<Long, Long> getConfirmedRequests(List<Event> events) {
        List<Long> eventsId = getPublished(events).stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        Map<Long, Long> requestStats = new HashMap<>();
        if (!eventsId.isEmpty()) {
            requestRepository.findConfirmedRequests(eventsId)
                    .forEach(stat -> requestStats.put(stat.getEventId(), stat.getConfirmedRequests()));
        }
        return requestStats;
    }

    private Optional<LocalDateTime> getMinPublishedOn(List<Event> publishedEvents) {
        return publishedEvents.stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo);
    }

    private List<String> getURIs(List<Event> publishedEvents) {
        return publishedEvents.stream()
                .map(Event::getId)
                .map(id -> ("/events/" + id))
                .collect(Collectors.toList());
    }

    private void putStats(Map<Long, Long> views, List<StatsDto> stats) {
        stats.forEach(stat -> {
            Long eventId = Long.parseLong(stat.getUri()
                    .split("/", 0)[2]);
            views.put(eventId, views.getOrDefault(eventId, 0L) + stat.getHits());
        });
    }

    private List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        ResponseEntity<Object> response = statsClient.getStats(start, end, uris, unique);
        try {
            return Arrays.asList(mapper.readValue(mapper.writeValueAsString(response.getBody()), StatsDto[].class));
        } catch (JsonProcessingException e) {
            throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private List<Event> getPublished(List<Event> events) {
        return events.stream()
                .filter(event -> event.getPublishedOn() != null)
                .collect(Collectors.toList());
    }
}