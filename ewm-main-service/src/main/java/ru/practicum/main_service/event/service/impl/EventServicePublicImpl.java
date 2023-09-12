package ru.practicum.main_service.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.event.dto.*;
import ru.practicum.main_service.event.mapper.EventMapper;
import ru.practicum.main_service.event.model.*;
import ru.practicum.main_service.event.service.EventServicePrivate;
import ru.practicum.main_service.event.service.EventServicePublic;
import ru.practicum.main_service.event.service.StatsService;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServicePublicImpl implements EventServicePublic {

    private final StatsService statsService;
    private final EventRepository eventRepository;
    private final EventServicePrivate eventServicePrivate;

    @Override
    public EventFullDto getPublicEventById(Long eventId, HttpServletRequest request) {
        Event event = eventServicePrivate.getEventById(eventId);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        statsService.addHit(request);
        return toEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getAllPublicEvents(
            String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Boolean onlyAvailable, EventSortType sort, Pageable pageable, HttpServletRequest request) {
        checkStartIsBeforeEnd(rangeStart, rangeEnd);
        List<Event> events = eventRepository.findAllByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
        statsService.addHit(request);
        return eventServicePrivate.toEventsShortDto(events);
    }

    private List<EventFullDto> toEventsFullDto(List<Event> events) {
        Map<Long, Long> views = statsService.getViews(events);
        Map<Long, Long> confirmedRequests = statsService.getConfirmedRequests(events);
        return events.stream()
                .map((event) -> EventMapper.INSTANCE.toEventFullDto(
                        event,
                        confirmedRequests.getOrDefault(event.getId(), 0L),
                        views.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());
    }

    private EventFullDto toEventFullDto(Event event) {
        return toEventsFullDto(List.of(event)).get(0);
    }

    private void checkStartIsBeforeEnd(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Dates are set incorrectly");
        }
    }
}