package ru.practicum.main_service.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.event.dto.EventFullDto;
import ru.practicum.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.main_service.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventServiceAdmin {
    List<EventFullDto> getAllEventsForAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    EventFullDto updateEventById(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);
}