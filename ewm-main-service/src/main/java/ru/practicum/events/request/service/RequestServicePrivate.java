package ru.practicum.events.request.service;

import ru.practicum.events.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RequestServicePrivate {
    List<ParticipationRequestDto> getAllRequestsUserById(Long userId);

    ParticipationRequestDto addRequestEventById(Long userId, Long eventId, HttpServletRequest request);

    ParticipationRequestDto updateRequestStatus(Long userId, Long requestId, HttpServletRequest request);
}
