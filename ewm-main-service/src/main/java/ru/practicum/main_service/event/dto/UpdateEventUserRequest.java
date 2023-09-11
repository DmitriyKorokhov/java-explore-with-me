package ru.practicum.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.main_service.event.model.EventStateAction;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.main_service.parameters.Constants.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, message = "The minimum number of characters for a Annotation is 20, the maximum is 2000")
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = "The minimum number of characters for a full Description of the event is 20, the maximum is 7000")
    private String description;
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
    @Valid
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero(message = "The Participant Limit of users cannot be negative")
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventStateAction stateAction;
    @Size(min = 3, max = 120, message = "The minimum number of characters for the Title is 3, the maximum is 120")
    private String title;
}