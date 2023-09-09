package ru.practicum.main_service.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.service.CategoryServicePublic;
import ru.practicum.main_service.event.dto.*;
import ru.practicum.main_service.event.mapper.EventMapper;
import ru.practicum.main_service.event.mapper.LocationMapper;
import ru.practicum.main_service.event.model.*;
import ru.practicum.main_service.event.service.EventServicePrivate;
import ru.practicum.main_service.event.service.StatsService;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.event.storage.LocationRepository;
import ru.practicum.main_service.exception.ConflictException;
import ru.practicum.main_service.exception.ValidationException;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.service.UserServiceAdmin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServicePrivateImpl implements EventServicePrivate {

    private final UserServiceAdmin userServiceAdmin;
    private final CategoryServicePublic categoryService;
    private final StatsService statsService;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    @Override
    public EventFullDto getPrivateEventByIdAndByUserId(Long userId, Long eventId) {
        userServiceAdmin.getUser(userId);
        Event event = getEventByIdAndInitiatorId(eventId, userId);
        return toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getAllPrivateEventsByUserId(Long userId, Pageable pageable) {
        userServiceAdmin.getUser(userId);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);
        return toEventsShortDto(events);
    }

    @Override
    @Transactional
    public EventFullDto addPrivateEventByUserId(Long userId, NewEventDto newEventDto) {
        checkNewEventDate(newEventDto.getEventDate(), LocalDateTime.now().plusHours(2));
        User eventUser = userServiceAdmin.getUser(userId);
        Category eventCategory = categoryService.getCategoryById(newEventDto.getCategory());
        Location eventLocation = getOrSaveLocation(newEventDto.getLocation());
        Event newEvent = EventMapper.toEvent(newEventDto, eventUser, eventCategory, eventLocation, LocalDateTime.now(),
                EventState.PENDING);
        Event ev = eventRepository.save(newEvent);
        return toEventFullDto(ev);
    }

    @Override
    @Transactional
    public EventFullDto updatePrivateEventByIdAndByUserId(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        checkNewEventDate(updateEventUserRequest.getEventDate(), LocalDateTime.now().plusHours(2));
        userServiceAdmin.getUser(userId);
        Event event = getEventByIdAndInitiatorId(eventId, userId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя изменять опубликованные события");
        }
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            event.setCategory(categoryService.getCategoryById(updateEventUserRequest.getCategory()));
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            event.setEventDate(updateEventUserRequest.getEventDate());
        }
        if (updateEventUserRequest.getLocation() != null) {
            event.setLocation(getOrSaveLocation(updateEventUserRequest.getLocation()));
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getStateAction() != null) {
            switch (updateEventUserRequest.getStateAction()) {
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
            }
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
        return toEventFullDto(eventRepository.save(event));
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Ресурс не найден"));
    }

    @Override
    public List<Event> getEventsByIds(List<Long> eventsId) {
        if (eventsId.isEmpty()) {
            return new ArrayList<>();
        }
        return eventRepository.findAllByIdIn(eventsId);
    }

    @Override
    public List<EventShortDto> toEventsShortDto(List<Event> events) {
        Map<Long, Long> views = statsService.getViews(events);
        Map<Long, Long> confirmedRequests = statsService.getConfirmedRequests(events);
        return events.stream()
                .map((event) -> EventMapper.toEventShortDto(
                        event,
                        confirmedRequests.getOrDefault(event.getId(), 0L),
                        views.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());
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

    private Event getEventByIdAndInitiatorId(Long eventId, Long userId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Ресурс не найден"));
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
