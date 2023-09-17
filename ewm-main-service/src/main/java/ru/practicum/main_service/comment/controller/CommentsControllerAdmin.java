package ru.practicum.main_service.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.comment.service.CommentsServiceAdmin;
import ru.practicum.main_service.parameters.EwmPageRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class CommentsControllerAdmin {

    private final CommentsServiceAdmin commentsServiceAdmin;

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCommentDto approveCommentByIdByAdmin(@PathVariable Long commentId) {
        log.info("Request for comment approval with id = {}", commentId);
        return commentsServiceAdmin.approveCommentByIdByAdmin(commentId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectCommentByIdByAdmin(@PathVariable Long commentId) {
        log.info("Request to reject a comment with id = {}", commentId);
        commentsServiceAdmin.rejectCommentByIdByAdmin(commentId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCommentDto> getAllComments(@RequestParam(defaultValue = "false") Boolean pending,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                   @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Request for all comments");
        return commentsServiceAdmin.getAllComments(pending, EwmPageRequest.of(from, size));
    }

    @GetMapping("events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCommentDto> getAllCommentsForAnEventByIdByAdmin(@PathVariable Long eventId,
                                                          @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                          @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Request for comments for an event with id = {}", eventId);
        return commentsServiceAdmin.getAllCommentsForAnEventByIdByAdmin(eventId, EwmPageRequest.of(from, size));
    }
}