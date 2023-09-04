package ru.practicum.events.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.UpdateEventAdminRequest;
import ru.practicum.events.event.service.EventServiceAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class EventControllerAdmin {

    private final EventServiceAdmin eventServiceAdmin;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<EventFullDto> getAllEventsForAdmin(@RequestParam(required = false) List<Long> users,
                                            @RequestParam(required = false) List<String> states,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @PositiveOrZero @RequestParam(defaultValue = "0", required = false) Integer from,
                                            @Positive @RequestParam(defaultValue = "10", required = false) Integer size,
                                            HttpServletRequest request) {

        return eventServiceAdmin.getAllEventsForAdmin(users, states, categories, rangeStart, rangeEnd, from, size, request);
    }

    @PatchMapping("/{eventId}")
    EventFullDto updateEventById(@PathVariable Long eventId,
                                 @RequestBody UpdateEventAdminRequest updateEventAdminRequest,
                                 HttpServletRequest request) {
        return eventServiceAdmin.updateEventById(eventId, updateEventAdminRequest, request);
    }
}