package ru.practicum.main_service.comment.service;

import ru.practicum.main_service.comment.dto.NewCommentDto;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.parameters.EwmPageRequest;

import java.util.List;

public interface CommentsServicePrivate {
    ResponseCommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    ResponseCommentDto updateCommentById(Long userId, Long commentId, NewCommentDto newCommentDto);

    List<ResponseCommentDto> getAllCommentsByUser(Long userId, EwmPageRequest of);

    void deleteCommentById(Long userId, Long commentId);
}
