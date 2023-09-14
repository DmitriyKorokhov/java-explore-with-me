package ru.practicum.main_service.comment.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.comment.model.Comment;
import ru.practicum.main_service.comment.model.CommentStatus;
import ru.practicum.main_service.parameters.EwmPageRequest;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByStatus(CommentStatus status);

    List<Comment> findAllByAuthorId(Long authorId, Pageable pageable);

    List<Comment> findAllByEventId(Long eventId, EwmPageRequest page);

    List<Comment> findAllByEventIdAndStatus(Long eventId, CommentStatus status, EwmPageRequest page);
}