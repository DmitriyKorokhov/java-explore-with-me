package ru.practicum.main_service.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static ru.practicum.main_service.parameters.Constants.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime created;
    @PositiveOrZero(message = "The Id events cannot be negative")
    private Long event;
    @PositiveOrZero(message = "The Id cannot be negative")
    private Long id;
    @PositiveOrZero(message = "The Id requesters cannot be negative")
    private Long requester;
    @NotBlank(message = "The Status should not be empty")
    private String status;
}