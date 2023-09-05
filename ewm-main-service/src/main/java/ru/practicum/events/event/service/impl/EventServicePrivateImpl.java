package ru.practicum.events.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;
import ru.practicum.events.event.dto.NewEventDto;
import ru.practicum.events.event.dto.UpdateEventUserRequest;
import ru.practicum.events.event.dto.stateDto.ActionStateDto;
import ru.practicum.events.event.mapper.EventMapper;
import ru.practicum.events.event.mapper.LocationMapper;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.model.EventState;
import ru.practicum.events.event.service.EventServicePrivate;
import ru.practicum.events.event.storage.EventRepository;
import ru.practicum.events.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.events.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.events.request.dto.ParticipationRequestDto;
import ru.practicum.events.request.dto.RequestStatusDto;
import ru.practicum.events.request.mapper.RequestMapper;
import ru.practicum.events.request.model.Request;
import ru.practicum.events.request.model.RequestStatus;
import ru.practicum.events.request.storage.RequestRepository;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.ConflictRequestException;
import ru.practicum.exception.ForbiddenEventException;
import ru.practicum.exception.ResourceNotFoundException;
import ru.practicum.users.model.User;
import ru.practicum.findobject.FindObjectInRepository;
import ru.practicum.formatter.DateFormatter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServicePrivateImpl implements EventServicePrivate {

    private final EventRepository eventRepository;
    private final FindObjectInRepository findObjectInRepository;
    private final RequestRepository requestRepository;
    private final ProcessingEvents processingEvents;

    @Override
    public List<EventShortDto> getAllPrivateEventsByUserId(Long userId, int from, int size, HttpServletRequest request) {
        log.info("Получен запрос на получение всех событий для пользователя с id= {}  (приватный)", userId);
        findObjectInRepository.getUserById(userId);
        Pageable pageable = PageRequest.of(from, size);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);
        List<Event> eventsAddViews = processingEvents.addViewsInEventsList(events, request);
        List<Event> newEvents = processingEvents.confirmedRequests(eventsAddViews);
        return newEvents.stream()
                .map(EventMapper::eventToeventShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto addPrivateEventByUserId(Long userId, NewEventDto newEventDto) {
        log.info("Получен запрос на добавление события пользователем с id= {} (приватный)", userId);
        checkEventDate(DateFormatter.formatDate(newEventDto.getEventDate()));
        User user = findObjectInRepository.getUserById(userId);
        Category category = findObjectInRepository.getCategoryById(newEventDto.getCategory());
        Long views = 0L;
        Long confirmedRequests = 0L;
        Event event = EventMapper.newEventDtoToCreateEvent(newEventDto, user, category, views, confirmedRequests);
        return getEventFullDto(event);
    }

    @Override
    public EventFullDto getPrivateEventByIdAndByUserId(Long userId, Long eventId, HttpServletRequest request) {
        log.info("Получен запрос на получение события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        User user = findObjectInRepository.getUserById(userId);
        Event event = findObjectInRepository.getEventById(eventId);
        checkOwnerEvent(event, user);
        addEventConfirmedRequestsAndViews(event, request);
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public EventFullDto updatePrivateEventByIdAndByUserId(Long userId, Long eventId, UpdateEventUserRequest updateEvent, HttpServletRequest request) {
        log.info("Получен запрос на обновление события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        if (updateEvent.getEventDate() != null) {
            checkEventDate(DateFormatter.formatDate(updateEvent.getEventDate()));
        }
        Event event = findObjectInRepository.getEventById(eventId);
        User user = findObjectInRepository.getUserById(userId);
        checkOwnerEvent(event, user);
        eventAvailability(event);
        if (updateEvent.getAnnotation() != null && !updateEvent.getAnnotation().isBlank()) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getCategory() != null) {
            Category category = findObjectInRepository.getCategoryById(updateEvent.getCategory());
            event.setCategory(category);
        }
        if (updateEvent.getDescription() != null && !updateEvent.getDescription().isBlank()) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getEventDate() != null) {
            event.setEventDate(DateFormatter.formatDate(updateEvent.getEventDate()));
        }
        if (updateEvent.getLocation() != null) {
            event.setLocation(LocationMapper.locationDtoToLocation(updateEvent.getLocation()));
        }
        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }
        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }
        if (updateEvent.getStateAction() != null) {
            event.setState(determiningTheStatusForEvent(updateEvent.getStateAction()));
        }
        if (updateEvent.getTitle() != null && !updateEvent.getTitle().isBlank()) {
            event.setTitle(updateEvent.getTitle());
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            addEventConfirmedRequestsAndViews(event, request);
        } else {
            event.setViews(0L);
            event.setConfirmedRequests(0L);
        }
        return getEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getAllPrivateEventsByRequests(Long userId, Long eventId, HttpServletRequest request) {
        log.info("Получен запрос на получение всех запросов для события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        try {
            Event event = findObjectInRepository.getEventById(eventId);
            User user = findObjectInRepository.getUserById(userId);
            checkOwnerEvent(event, user);
            List<Request> requests = requestRepository.findAllByEvent(event);
            return requests.stream().map(RequestMapper::requestToParticipationRequestDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException("Некорректный запрос получения списка запросов на участие в текущем событии");
        }
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequest, HttpServletRequest request) {
        log.info("Получен запрос на обновление статуса запроса для события с id= {} для пользователя с id= {} (приватный)", eventId, userId);
        Event event = findObjectInRepository.getEventById(eventId);
        User user = findObjectInRepository.getUserById(userId);
        checkOwnerEvent(event, user);
        if (event.getState().equals(EventState.PUBLISHED)) {
            addEventConfirmedRequestsAndViews(event, request);
        } else {
            event.setViews(0L);
            event.setConfirmedRequests(0L);
        }
        if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
            log.warn("Достигнут лимит по заявкам на данное событие с id= {}", eventId);
            throw new ForbiddenEventException("Достигнут лимит по заявкам на данное событие с id= " + eventId);
        }
        List<Request> requests = getAllRequestsContainsIds(eventRequest.getRequestIds());
        if (event.getParticipantLimit() == 0 || !event.isRequestModeration()) {
            log.info("Подтверждение заявок не требуется");
            return new EventRequestStatusUpdateResult(new ArrayList<>(), new ArrayList<>());
        } else if (eventRequest.getStatus().equals(RequestStatusDto.CONFIRMED)) {
            log.info("Запрос на подтверждение заявок");
            return considerationOfRequests(event, requests);
        } else if (eventRequest.getStatus().equals(RequestStatusDto.REJECTED)) {
            log.info("Запрос на отклонение заявок");
            EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(new ArrayList<>(), new ArrayList<>());
            List<ParticipationRequestDto> rejectedRequests = addRejectedAllRequests(requests);
            result.getRejectedRequests().addAll(rejectedRequests);
            return result;
        }
        return new EventRequestStatusUpdateResult(new ArrayList<>(), new ArrayList<>());
    }

    private void checkEventDate(LocalDateTime eventDate) {
        if (eventDate != null) {
            LocalDateTime timeNow = LocalDateTime.now().plusHours(2L);
            if (eventDate.isBefore(timeNow)) {
                throw new ForbiddenEventException("Событие должно содержать дату, которая еще не наступила. " +
                        "Value: " + eventDate);
            }
        }
    }

    private void checkOwnerEvent(Event event, User user) {
        if (!Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ForbiddenEventException("Событие с id=" + event.getId() + " не принадлежит пользователю с id=" + user.getId());
        }
    }

    private EventState determiningTheStatusForEvent(ActionStateDto stateAction) {
        if (stateAction.equals(ActionStateDto.SEND_TO_REVIEW)) {
            return EventState.PENDING;
        } else if (stateAction.equals(ActionStateDto.CANCEL_REVIEW)) {
            return EventState.CANCELED;
        } else if (stateAction.equals(ActionStateDto.PUBLISH_EVENT)) {
            return EventState.PUBLISHED;
        } else if (stateAction.equals(ActionStateDto.REJECT_EVENT)) {
            return EventState.CANCELED;
        } else {
            throw new BadRequestException("Статус не соответствует модификатору доступа");
        }
    }

    private void eventAvailability(Event event) {
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenEventException("Статус события не позволяет редоктировать событие, статус: " + event.getState());
        }
    }

    private List<ParticipationRequestDto> addRejectedAllRequests(List<Request> requests) {
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        for (Request req : requests) {
            if (!req.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictRequestException("Статус заявки " + req.getId() + " не позволяет ее одобрить, статус равен " + req.getStatus());
            }
            req.setStatus(RequestStatus.REJECTED);
            requestRepository.save(req);
            rejectedRequests.add(RequestMapper.requestToParticipationRequestDto(req));
        }
        return rejectedRequests;
    }

    private List<Request> getAllRequestsContainsIds(List<Long> requestIds) {
        return requestRepository.findAllByIdIsIn(requestIds);
    }

    private EventRequestStatusUpdateResult considerationOfRequests(Event event, List<Request> requests) {
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        long count = processingEvents.confirmedRequestsForOneEvent(event, RequestStatus.CONFIRMED);
        event.setConfirmedRequests(count);
        for (Request req : requests) {
            if (!req.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictRequestException("Статус заявки " + req.getId() + " не позволяет ее одобрить, статус равен " + req.getStatus());
            }
            if (event.getConfirmedRequests() <= event.getParticipantLimit()) {
                req.setStatus(RequestStatus.CONFIRMED);
                confirmedRequests.add(RequestMapper.requestToParticipationRequestDto(req));
                requestRepository.save(req);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1L);
            } else {
                req.setStatus(RequestStatus.REJECTED);
                rejectedRequests.add(RequestMapper.requestToParticipationRequestDto(req));
                requestRepository.save(req);
            }
        }
        eventRepository.save(event);
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    private EventFullDto getEventFullDto(Event event) {
        try {
            return EventMapper.eventToEventFullDto(eventRepository.save(event));
        } catch (DataAccessException e) {
            throw new ResourceNotFoundException("База данных недоступна");
        } catch (Exception e) {
            throw new BadRequestException("Запрос на добавлении события " + event + " составлен не корректно ");
        }
    }

    private void addEventConfirmedRequestsAndViews(Event event, HttpServletRequest request) {
        long count = processingEvents.confirmedRequestsForOneEvent(event, RequestStatus.CONFIRMED);
        event.setConfirmedRequests(count);
        long views = processingEvents.searchViews(event, request);
        event.setViews(views);
    }
}