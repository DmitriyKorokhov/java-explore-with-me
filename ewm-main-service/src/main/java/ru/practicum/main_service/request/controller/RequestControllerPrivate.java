package ru.practicum.main_service.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.main_service.request.service.RequestServicePrivate;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestControllerPrivate {

    private final RequestServicePrivate requestServicePrivate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequestEventById(@PathVariable Long userId,
                                                       @RequestParam Long eventId) {
        log.info("Request to add a request from a user with id = {} for an event with id = {}", userId, eventId);
        return requestServicePrivate.addRequestEventById(userId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getAllRequestsUserById(@PathVariable Long userId) {
        log.info("Request to receive all user requests with id = {}", userId);
        return requestServicePrivate.getAllRequestsUserById(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequestUserById(@PathVariable Long userId,
                                                         @PathVariable Long requestId) {
        log.info("Request from a user with id = {} to cancel a request with id = {}", userId, requestId);
        return requestServicePrivate.cancelRequestUserById(userId, requestId);
    }
}