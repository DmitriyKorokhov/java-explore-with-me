package ru.practicum.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.event.dto.*;
import ru.practicum.main_service.event.service.EventServicePrivate;
import ru.practicum.main_service.request.service.RequestServicePrivate;
import ru.practicum.main_service.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main_service.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.main_service.parameters.EwmPageRequest;

import javax.validation.Valid;
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
    private final RequestServicePrivate requestServicePrivate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addPrivateEventByUserId(@PathVariable Long userId,
                                                @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Request to add an event by a user with id = {} (private)", userId);
        return eventServicePrivate.addPrivateEventByUserId(userId, newEventDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllPrivateEventsByUserId(@PathVariable Long userId,
                                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Request to receive all events for a user with id = {} (private)", userId);
        return eventServicePrivate.getAllPrivateEventsByUserId(userId, EwmPageRequest.of(from, size));
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getPrivateEventByIdAndByUserId(@PathVariable Long userId,
                                                       @PathVariable Long eventId) {
        log.info("Request to receive an event with id= {} for a user with id = {} (private)", eventId, userId);
        return eventServicePrivate.getPrivateEventByIdAndByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updatePrivateEventByIdAndByUserId(@PathVariable Long userId,
                                                          @PathVariable Long eventId,
                                                          @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Request to update an event with id = {} for a user with id = {} (private)", eventId, userId);
        return eventServicePrivate.updatePrivateEventByIdAndByUserId(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getAllPrivateEventsByRequests(@PathVariable Long userId,
                                                                       @PathVariable Long eventId) {
        log.info("Request to receive all requests for an event with id= {} " +
                "for a user with id= {} (private)", eventId, userId);
        return requestServicePrivate.getAllPrivateEventsByRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateEventRequestStatus(@PathVariable Long userId,
                                                                   @PathVariable Long eventId,
                                @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Request to update the status of a request for an event with id = {} " +
                 "for a user with id = {} (private)", eventId, userId);
        return requestServicePrivate.updateEventRequestStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }
}