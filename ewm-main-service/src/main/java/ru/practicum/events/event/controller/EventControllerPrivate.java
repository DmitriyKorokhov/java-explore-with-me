package ru.practicum.events.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;
import ru.practicum.events.event.dto.NewEventDto;
import ru.practicum.events.event.dto.UpdateEventUserRequest;
import ru.practicum.events.event.service.EventServicePrivate;
import ru.practicum.events.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.events.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.events.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class EventControllerPrivate {

    private final EventServicePrivate eventServicePrivate;

    @GetMapping()
    List<EventShortDto> getAllPrivateEventsByUser(@PathVariable Long userId,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(defaultValue = "10") Integer size,
                                                  HttpServletRequest request) {
        return eventServicePrivate.getAllPrivateEventsByUserId(userId, from, size, request);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto addPrivateEventByUserId(@PathVariable Long userId,
                                         @Validated @RequestBody NewEventDto newEventDto) {
        return eventServicePrivate.addPrivateEventByUserId(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    EventFullDto getPrivateEventByIdAndByUserId(@PathVariable Long userId,
                                                @PathVariable Long eventId,
                                                HttpServletRequest request) {
        return eventServicePrivate.getPrivateEventByIdAndByUserId(userId, eventId, request);
    }

    @PatchMapping("/{eventId}")
    EventFullDto updatePrivateEventByIdAndByUserId(@PathVariable Long userId,
                                                   @PathVariable Long eventId,
                                                   @Validated @RequestBody UpdateEventUserRequest updateEventUserRequest,
                                                   HttpServletRequest request) {
        return eventServicePrivate.updatePrivateEventByIdAndByUserId(userId, eventId, updateEventUserRequest, request);
    }

    @GetMapping("/{eventId}/requests")
    List<ParticipationRequestDto> getAllPrivateEventsByRequests(@PathVariable Long userId,
                                                                @PathVariable Long eventId,
                                                                HttpServletRequest request) {
        return eventServicePrivate.getAllPrivateEventsByRequests(userId, eventId, request);
    }

    @PatchMapping("/{eventId}/requests")
    EventRequestStatusUpdateResult updateEventRequestStatus(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @RequestBody EventRequestStatusUpdateRequest eventRequest,
                                                            HttpServletRequest request) {
        return eventServicePrivate.updateEventRequestStatus(userId, eventId, eventRequest, request);
    }
}