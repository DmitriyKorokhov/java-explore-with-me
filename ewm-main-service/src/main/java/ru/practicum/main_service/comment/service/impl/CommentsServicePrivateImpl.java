package ru.practicum.main_service.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.comment.dto.NewCommentDto;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.comment.mapper.CommentMapper;
import ru.practicum.main_service.comment.model.Comment;
import ru.practicum.main_service.comment.service.CommentsServicePrivate;
import ru.practicum.main_service.comment.storage.CommentRepository;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.model.EventState;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.exception.ConflictException;
import ru.practicum.main_service.exception.ValidationException;
import ru.practicum.main_service.parameters.EwmPageRequest;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.storage.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsServicePrivateImpl implements CommentsServicePrivate {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public ResponseCommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User user = getUser(userId);
        Event event = getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Request for an unpublished event");
        }
        Comment newComment = CommentMapper.INSTANCE.toComment(user, event, newCommentDto);
        return CommentMapper.INSTANCE.toResponseCommentDto(commentRepository.save(newComment));
    }

    @Override
    @Transactional
    public ResponseCommentDto updateCommentById(Long userId, Long commentId, NewCommentDto newCommentDto) {
        getUser(userId);
        Comment comment = getComment(commentId);
        checkOwner(comment.getAuthor().getId(), userId);
        comment.setText(newCommentDto.getText());
        return CommentMapper.INSTANCE.toResponseCommentDto(comment);
    }

    @Override
    public List<ResponseCommentDto> getAllCommentsByUser(Long userId, EwmPageRequest page) {
        getUser(userId);
        List<Comment> result = commentRepository.findAllByAuthorId(userId, page);
        return CommentMapper.INSTANCE.toResponseCommentsDto(result);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long userId, Long commentId) {
        getUser(userId);
        Comment comment = getComment(commentId);
        checkOwner(comment.getAuthor().getId(), userId);
        commentRepository.deleteById(commentId);
    }

    private void checkOwner(Long authorId, Long userId) {
        if (!Objects.equals(authorId, userId)) {
            throw new ConflictException("The user is not the owner");
        }
    }

    private User getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Resource not found"));
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Resource not found"));
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Resource not found"));
    }
}