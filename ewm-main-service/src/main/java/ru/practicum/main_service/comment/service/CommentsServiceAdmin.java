package ru.practicum.main_service.comment.service;

import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.parameters.EwmPageRequest;

import java.util.List;

public interface CommentsServiceAdmin {
    ResponseCommentDto approveCommentByIdByAdmin(Long commentId);

    List<ResponseCommentDto> getAllComments(Boolean pending, EwmPageRequest of);

    void rejectCommentByIdByAdmin(Long commentId);

    List<ResponseCommentDto> getAllCommentsForAnEventByIdByAdmin(Long eventId, EwmPageRequest page);
}