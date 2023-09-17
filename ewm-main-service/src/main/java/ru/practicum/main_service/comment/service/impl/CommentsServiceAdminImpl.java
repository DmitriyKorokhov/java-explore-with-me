package ru.practicum.main_service.comment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.comment.mapper.CommentMapper;
import ru.practicum.main_service.comment.model.Comment;
import ru.practicum.main_service.comment.model.CommentStatus;
import ru.practicum.main_service.comment.service.CommentsServiceAdmin;
import ru.practicum.main_service.comment.storage.CommentRepository;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.exception.ValidationException;
import ru.practicum.main_service.parameters.EwmPageRequest;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsServiceAdminImpl implements CommentsServiceAdmin {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public ResponseCommentDto approveCommentByIdByAdmin(Long commentId) {
        Comment comment = getComment(commentId);
        comment.setStatus(CommentStatus.APPROVED);
        return CommentMapper.INSTANCE.toResponseCommentDto(comment);
    }

    @Override
    public List<ResponseCommentDto> getAllComments(Boolean pending, EwmPageRequest page) {
        if (pending) {
            return CommentMapper.INSTANCE.toResponseCommentsDto(commentRepository.findAllByStatus(CommentStatus.UNDER_CONSIDERATION));
        } else {
            return CommentMapper.INSTANCE.toResponseCommentsDto(commentRepository.findAll());
        }
    }

    @Override
    public List<ResponseCommentDto> getAllCommentsForAnEventByIdByAdmin(Long eventId, EwmPageRequest page) {
        getEventById(eventId);
        List<Comment> listOfComments = commentRepository.findAllByEventIdAndStatus(eventId, CommentStatus.UNDER_CONSIDERATION, page);
        return CommentMapper.INSTANCE.toResponseCommentsDto(listOfComments);
    }

    @Override
    @Transactional
    public void rejectCommentByIdByAdmin(Long commentId) {
        getComment(commentId);
        commentRepository.deleteById(commentId);
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
                    log.error("The Event does not exist");
                    return new ValidationException(HttpStatus.NOT_FOUND, "Resource not found");
                });
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
                    log.error("The Comment does not exist");
                    return new ValidationException(HttpStatus.NOT_FOUND, "Resource not found");
                });
    }
}