package ru.practicum.events.event.service;

import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.UpdateEventAdminRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventServiceAdmin {
    List<EventFullDto> getAllEventsForAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size, HttpServletRequest request);

    EventFullDto updateEventById(Long eventId, UpdateEventAdminRequest updateEventAdminRequest, HttpServletRequest request);
}
