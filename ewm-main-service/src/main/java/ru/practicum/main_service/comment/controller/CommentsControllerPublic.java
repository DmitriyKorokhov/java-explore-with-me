package ru.practicum.main_service.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.comment.service.CommentsServicePublic;
import ru.practicum.main_service.parameters.EwmPageRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentsControllerPublic {

    private final CommentsServicePublic commentsServicePublic;

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCommentDto getCommentById(@PathVariable Long commentId) {
        log.info("Request for a comment with id = {}", commentId);
        return commentsServicePublic.getCommentById(commentId);
    }

    @GetMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCommentDto> getCommentsForAnEventByUser(@PathVariable Long eventId,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Request for comments for an event with id = {}", eventId);
        return commentsServicePublic.getCommentsForAnEventByUser(eventId, EwmPageRequest.of(from, size));
    }
}