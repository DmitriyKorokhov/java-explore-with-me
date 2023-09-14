package ru.practicum.main_service.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.comment.mapper.CommentMapper;
import ru.practicum.main_service.comment.model.Comment;
import ru.practicum.main_service.comment.service.CommentsServicePublic;
import ru.practicum.main_service.comment.storage.CommentRepository;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.model.EventState;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.exception.ConflictException;
import ru.practicum.main_service.exception.ValidationException;
import ru.practicum.main_service.parameters.EwmPageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsServicePublicImpl implements CommentsServicePublic {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public ResponseCommentDto getCommentById(Long id) {
        return CommentMapper.INSTANCE.toResponseCommentDto(getComment(id));
    }

    @Override
    public List<ResponseCommentDto> getCommentsForAnEventByUser(Long eventId, EwmPageRequest page) {
        Event event = getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Request for an unpublished event");
        }
        return CommentMapper.INSTANCE.toResponseCommentsDto(commentRepository.findAllByEventId(eventId, page));
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