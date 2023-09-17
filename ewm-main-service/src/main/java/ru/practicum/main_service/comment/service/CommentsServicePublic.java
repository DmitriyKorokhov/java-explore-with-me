package ru.practicum.main_service.comment.service;

import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.parameters.EwmPageRequest;

import java.util.List;

public interface CommentsServicePublic {
    ResponseCommentDto getCommentById(Long commentId);

    List<ResponseCommentDto> getCommentsForAnEventByUser(Long eventId, EwmPageRequest of);
}
