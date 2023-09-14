package ru.practicum.main_service.request.service;

import ru.practicum.main_service.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main_service.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main_service.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestServicePrivate {
    ParticipationRequestDto addRequestEventById(Long userId, Long eventId);

    List<ParticipationRequestDto> getAllPrivateEventsByRequests(Long userId, Long eventId);

    List<ParticipationRequestDto> getAllRequestsUserById(Long userId);

    ParticipationRequestDto cancelRequestUserById(Long userId, Long requestId);

    EventRequestStatusUpdateResult updateRequestsByEventOwner(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}