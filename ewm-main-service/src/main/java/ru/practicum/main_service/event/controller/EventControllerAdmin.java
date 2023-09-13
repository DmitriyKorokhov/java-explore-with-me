package ru.practicum.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.event.dto.EventFullDto;
import ru.practicum.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.main_service.event.model.EventState;
import ru.practicum.main_service.event.service.EventServiceAdmin;
import ru.practicum.main_service.parameters.EwmPageRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main_service.parameters.Constants.DATE_FORMAT;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventControllerAdmin {

    private final EventServiceAdmin eventServiceAdmin;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllEventsForAdmin(@RequestParam(required = false) List<Long> users,
                                                   @RequestParam(required = false) List<EventState> states,
                                                   @RequestParam(required = false) List<Long> categories,
                                                   @RequestParam(required = false)
                                                       @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime rangeStart,
                                                   @RequestParam(required = false)
                                                       @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime rangeEnd,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                   @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Request to search for all events (administrator)");
        return eventServiceAdmin.getAllEventsForAdmin(users, states, categories, rangeStart, rangeEnd, EwmPageRequest.of(from, size));
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventById(@PathVariable Long eventId,
                                        @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Event update request with id = {} (administrator)", eventId);
        return eventServiceAdmin.updateEventById(eventId, updateEventAdminRequest);
    }
}