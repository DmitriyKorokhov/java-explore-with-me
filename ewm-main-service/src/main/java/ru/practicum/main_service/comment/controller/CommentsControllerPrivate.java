package ru.practicum.main_service.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.comment.dto.NewCommentDto;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.comment.service.CommentsServicePrivate;
import ru.practicum.main_service.parameters.EwmPageRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/comments")
public class CommentsControllerPrivate {

    private final CommentsServicePrivate commentsServicePrivate;

    @PostMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCommentDto addComment(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Request to create a user comment with id = {}", userId);
        return commentsServicePrivate.addComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCommentDto updateCommentById(@PathVariable Long userId,
                                                @PathVariable Long commentId,
                                                @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Request to change a comment with id = {}", commentId);
        return commentsServicePrivate.updateCommentById(userId, commentId, newCommentDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCommentDto> getAllCommentsByUser(@PathVariable Long userId,
                                                         @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                         @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Request for user comments with id = {}", userId);
        return commentsServicePrivate.getAllCommentsByUser(userId, EwmPageRequest.of(from, size));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long userId,
                                  @PathVariable Long commentId) {
        log.info("Request to delete a comment with id = {}", commentId);
        commentsServicePrivate.deleteCommentById(userId, commentId);
    }
}