package ru.practicum.main_service.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.event.dto.*;
import ru.practicum.main_service.event.model.Event;

import java.util.List;

public interface EventServicePrivate {
    EventFullDto addPrivateEventByUserId(Long userId, NewEventDto newEventDto);

    EventFullDto getPrivateEventByIdAndByUserId(Long userId, Long eventId);

    List<EventShortDto> getAllPrivateEventsByUserId(Long userId, Pageable pageable);

    EventFullDto updatePrivateEventByIdAndByUserId(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    Event getEventById(Long eventId);

    List<Event> getEventsByIds(List<Long> eventsId);

    List<EventShortDto> toEventsShortDto(List<Event> events);
}