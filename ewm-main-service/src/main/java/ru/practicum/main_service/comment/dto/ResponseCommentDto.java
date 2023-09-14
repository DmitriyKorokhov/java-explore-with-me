package ru.practicum.main_service.comment.dto;

import lombok.*;
import ru.practicum.main_service.comment.model.CommentStatus;
import ru.practicum.main_service.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommentDto {
    private Long id;
    @NotBlank(message = "The Text should not be empty")
    private String text;
    @NotNull(message = "The Creation time must exist")
    private LocalDateTime created;
    @NotNull(message = "The Author must exist")
    private UserShortDto author;
    @PositiveOrZero(message = "The Event Id must exist")
    private Long eventId;
    private CommentStatus status;
}