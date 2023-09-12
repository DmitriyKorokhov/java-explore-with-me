package ru.practicum.main_service.request.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.event.service.EventServicePrivate;
import ru.practicum.main_service.event.service.StatsService;
import ru.practicum.main_service.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main_service.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main_service.request.dto.ParticipationRequestDto;
import ru.practicum.main_service.request.dto.RequestStatusAction;
import ru.practicum.main_service.request.mapper.RequestMapper;
import ru.practicum.main_service.event.model.*;
import ru.practicum.main_service.request.service.RequestServicePrivate;
import ru.practicum.main_service.request.storage.RequestRepository;
import ru.practicum.main_service.request.model.Request;
import ru.practicum.main_service.request.model.RequestStatus;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.service.UserServiceAdmin;
import ru.practicum.main_service.exception.ConflictException;
import ru.practicum.main_service.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServicePrivatePrivateImpl implements RequestServicePrivate {

    private final UserServiceAdmin userServiceAdmin;
    private final EventServicePrivate eventServicePrivate;
    private final StatsService statsService;
    private final RequestRepository requestRepository;

    @Override
    @Transactional
    public ParticipationRequestDto addRequestEventById(Long userId, Long eventId) {
        User user = userServiceAdmin.getUser(userId);
        Event event = eventServicePrivate.getEventById(eventId);
        checkEvent(event, userId);
        Request newRequest = Request.builder()
                .event(event)
                .requester(user)
                .created(LocalDateTime.now())
                .build();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            newRequest.setStatus(RequestStatus.CONFIRMED);
        } else {
            newRequest.setStatus(RequestStatus.PENDING);
        }
        return RequestMapper.INSTANCE.toParticipationRequestDto(requestRepository.save(newRequest));
    }

    @Override
    public List<ParticipationRequestDto> getAllRequestsUserById(Long userId) {
        userServiceAdmin.getUser(userId);
        return toParticipationRequestsDto(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequestUserById(Long userId, Long requestId) {
        userServiceAdmin.getUser(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Resource not found"));
        checkUserIsOwner(request.getRequester().getId(), userId);
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.INSTANCE.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getAllPrivateEventsByRequests(Long userId, Long eventId) {
        userServiceAdmin.getUser(userId);
        Event event = eventServicePrivate.getEventById(eventId);
        checkUserIsOwner(event.getInitiator().getId(), userId);
        return toParticipationRequestsDto(requestRepository.findAllByEventId(eventId));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequestStatus(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        userServiceAdmin.getUser(userId);
        Event event = eventServicePrivate.getEventById(eventId);
        List<Long> requestIds = eventRequestStatusUpdateRequest.getRequestIds();

        checkUserIsOwner(event.getInitiator().getId(), userId);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0 || requestIds.isEmpty()) {
            return new EventRequestStatusUpdateResult(List.of(), List.of());
        }

        List<Request> requests = requestRepository.findAllByIdIn(requestIds);
        checkRequests(requests, requestIds);

        List<Request> confirmedList = new ArrayList<>();
        List<Request> rejectedList = new ArrayList<>();

        if (eventRequestStatusUpdateRequest.getStatus().equals(RequestStatusAction.REJECTED)) {
            rejectedList.addAll(changeStatusAndSave(requests, RequestStatus.REJECTED));
        } else {
            Long newConfirmedRequests = statsService.getConfirmedRequests(List.of(event)).getOrDefault(eventId, 0L) +
                    requestIds.size();
            checkEventLimit(newConfirmedRequests, event.getParticipantLimit());
            confirmedList.addAll(changeStatusAndSave(requests, RequestStatus.CONFIRMED));
            if (newConfirmedRequests >= event.getParticipantLimit()) {
                rejectedList.addAll(changeStatusAndSave(
                        requestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.PENDING), RequestStatus.REJECTED)
                );
            }
        }
        return new EventRequestStatusUpdateResult(toParticipationRequestsDto(confirmedList), toParticipationRequestsDto(rejectedList));
    }

    private void checkRequests(List<Request> requests, List<Long> requestIds) {
        if (requests.size() != requestIds.size()) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        if (!requests.stream().map(Request::getStatus).allMatch(RequestStatus.PENDING::equals)) {
            throw new ConflictException("The application is not in the waiting status");
        }
    }

    private void checkEvent(Event event, Long userId) {
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException("Application for participation in your own event");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Request for an unpublished event");
        }
        Optional<Request> oldRequest = requestRepository.findByEventIdAndRequesterId(event.getId(), userId);
        if (oldRequest.isPresent()) {
            throw new ConflictException("Repeat request");
        }
        checkEventLimit(statsService.getConfirmedRequests(List.of(event)).getOrDefault(event.getId(), 0L) + 1,
                event.getParticipantLimit()
        );
    }

    private List<ParticipationRequestDto> toParticipationRequestsDto(List<Request> requests) {
        return requests.stream()
                .map(RequestMapper.INSTANCE::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    private List<Request> changeStatusAndSave(List<Request> requests, RequestStatus status) {
        requests.forEach(request -> request.setStatus(status));
        return requestRepository.saveAll(requests);
    }

    private void checkEventLimit(Long newLimit, Integer eventLimit) {
        if (eventLimit != 0 && (newLimit > eventLimit)) {
            throw new ConflictException("Exceeded the limit");
        }
    }

    private void checkUserIsOwner(Long id, Long userId) {
        if (!Objects.equals(id, userId)) {
            throw new ConflictException("The user is not the owner");
        }
    }
}