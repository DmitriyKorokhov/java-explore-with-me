package ru.practicum.main_service.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.category.service.CategoryServicePublic;
import ru.practicum.main_service.event.dto.*;
import ru.practicum.main_service.event.mapper.EventMapper;
import ru.practicum.main_service.event.mapper.LocationMapper;
import ru.practicum.main_service.event.model.*;
import ru.practicum.main_service.event.service.EventServiceAdmin;
import ru.practicum.main_service.event.service.EventServicePrivate;
import ru.practicum.main_service.event.service.StatsService;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.event.storage.LocationRepository;
import ru.practicum.main_service.exception.ConflictException;
import ru.practicum.main_service.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceAdminImpl implements EventServiceAdmin {

    private final CategoryServicePublic categoryService;
    private final StatsService statsService;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final EventServicePrivate eventServicePrivate;

    @Override
    public List<EventFullDto> getAllEventsForAdmin(List<Long> users, List<EventState> states, List<Long> categories, LocalDateTime rangeStart,
                                                   LocalDateTime rangeEnd, Pageable page) {
        checkStartIsBeforeEnd(rangeStart, rangeEnd);
        List<Event> events = eventRepository.findAllByAdmin(page, users, states, categories, rangeStart, rangeEnd);
        return toEventsFullDto(events);
    }

    @Override
    @Transactional
    public EventFullDto updateEventById(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        checkNewEventDate(updateEventAdminRequest.getEventDate(), LocalDateTime.now().plusHours(1));
        Event event = eventServicePrivate.getEventById(eventId);
        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }
        if (updateEventAdminRequest.getCategory() != null) {
            event.setCategory(categoryService.getCategoryById(updateEventAdminRequest.getCategory()));
        }
        if (updateEventAdminRequest.getEventDate() != null) {
            event.setEventDate(updateEventAdminRequest.getEventDate());
        }
        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }
        if (updateEventAdminRequest.getLocation() != null) {
            event.setLocation(getOrSaveLocation(updateEventAdminRequest.getLocation()));
        }
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            checkIsNewLimitNotLessOld(updateEventAdminRequest.getParticipantLimit(),
                    statsService.getConfirmedRequests(List.of(event)).getOrDefault(eventId, 0L));
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }
        if (updateEventAdminRequest.getStateAction() != null) {
            if (!event.getState().equals(EventState.PENDING)) {
                throw new ConflictException("Событие не находится в статусе ожидания");
            }
            if (updateEventAdminRequest.getStateAction() == EventStateAction.PUBLISH_EVENT) {
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else if (updateEventAdminRequest.getStateAction() == EventStateAction.REJECT_EVENT) {
                event.setState(EventState.CANCELED);
            }
        }
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }
        return toEventFullDto(eventRepository.save(event));
    }

    private List<EventFullDto> toEventsFullDto(List<Event> events) {
        Map<Long, Long> views = statsService.getViews(events);
        Map<Long, Long> confirmedRequests = statsService.getConfirmedRequests(events);
        return events.stream()
                .map((event) -> EventMapper.toEventFullDto(
                        event,
                        confirmedRequests.getOrDefault(event.getId(), 0L),
                        views.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());
    }

    private EventFullDto toEventFullDto(Event event) {
        return toEventsFullDto(List.of(event)).get(0);
    }

    private Location getOrSaveLocation(LocationDto locationDto) {
        Location newLocation = LocationMapper.toLocation(locationDto);
        return locationRepository.findByLatAndLon(newLocation.getLat(), newLocation.getLon())
                .orElseGet(() -> locationRepository.save(newLocation));
    }

    private void checkStartIsBeforeEnd(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Неверно заданы даты");
        }
    }

    private void checkNewEventDate(LocalDateTime newEventDate, LocalDateTime minTimeBeforeEventStart) {
        if (newEventDate != null && newEventDate.isBefore(minTimeBeforeEventStart)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Время не может быть раньше, чем через два часа от текущего момента");
        }
    }

    private void checkIsNewLimitNotLessOld(Integer newLimit, Long eventParticipantLimit) {
        if (newLimit != 0 && eventParticipantLimit != 0 && (newLimit < eventParticipantLimit)) {
            throw new ConflictException("Лимит уже достигнут");
        }
    }
}