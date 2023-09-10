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
        log.info("Получен запрос на добавление запроса от пользователя с id= {} для события с id= {}", userId, eventId);
        return requestServicePrivate.addRequestEventById(userId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getAllRequestsUserById(@PathVariable Long userId) {
        log.info("Получен запрос на получение всех запросов пользователя с id= " + userId);
        return requestServicePrivate.getAllRequestsUserById(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequestUserById(@PathVariable Long userId,
                                                         @PathVariable Long requestId) {
        log.info("Получен запрос от пользователя с id= {} на отмену запроса с id= {}", userId, requestId);
        return requestServicePrivate.cancelRequestUserById(userId, requestId);
    }
}