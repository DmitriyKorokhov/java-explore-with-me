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

    private final EventServicePrivate eventService;
    private final RequestServicePrivate requestServicePrivate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addPrivateEventByUserId(@PathVariable Long userId,
                                                @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос на добавление события пользователем с id= {} (приватный)", userId);
        return eventService.addPrivateEventByUserId(userId, newEventDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllPrivateEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Получен запрос на получение всех событий для пользователя с id= {}  (приватный)", userId);
        return eventService.getAllPrivateEventsByUserId(userId, EwmPageRequest.of(from, size));
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getPrivateEventByIdAndByUserId(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Получен запрос на получение события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        return eventService.getPrivateEventByIdAndByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updatePrivateEventByIdAndByUserId(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Получен запрос на обновление события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        return eventService.updatePrivateEventByIdAndByUserId(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getAllPrivateEventsByRequests(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Получен запрос на получение всех запросов для события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        return requestServicePrivate.getAllPrivateEventsByRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateEventRequestStatus(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Получен запрос на обновление статуса запроса для события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        return requestServicePrivate.updateEventRequestStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }
}