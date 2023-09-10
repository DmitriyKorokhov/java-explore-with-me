package ru.practicum.main_service.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.main_service.event.dto.RequestStats;
import ru.practicum.main_service.request.model.Request;
import ru.practicum.main_service.request.model.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long requesterId);

    Optional<Request> findByEventIdAndRequesterId(Long eventId, Long userId);

    List<Request> findAllByEventIdAndStatus(Long eventId, RequestStatus status);

    List<Request> findAllByEventId(Long eventId);

    List<Request> findAllByIdIn(List<Long> requestIds);

    @Query("SELECT new ru.practicum.main_service.event.dto.RequestStats(r.event.id, count(r.id)) " +
            "FROM Request AS r " +
            "WHERE r.event.id IN (:eventsId) " +
            "AND r.status = 'CONFIRMED' " +
            "GROUP BY r.event.id")
    List<RequestStats> findConfirmedRequests(@Param("eventsId") List<Long> eventsId);
}