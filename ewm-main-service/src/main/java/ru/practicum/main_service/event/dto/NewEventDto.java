package ru.practicum.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.main_service.parameters.Constants.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotBlank(message = "The Annotation should not be empty")
    @Size(min = 20, max = 2000, message = "The minimum number of characters for a Annotation is 20, the maximum is 2000")
    private String annotation;
    @NotNull(message = "The Category must exist")
    private Long category;
    @NotBlank(message = "The Description should not be empty")
    @Size(min = 20, max = 7000, message = "The minimum number of characters for a full Description of the event is 20, the maximum is 7000")
    private String description;
    @NotNull(message = "The Event Date should not be empty")
    @JsonFormat(pattern = DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
    @NotNull(message = "The Location must exist")
    private LocationDto location;
    private Boolean paid = false;
    @PositiveOrZero(message = "The Participant Limit of users cannot be negative")
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;
    @NotBlank(message = "The Title should not be empty")
    @Size(min = 3, max = 120, message = "The minimum number of characters for the Title is 3, the maximum is 120")
    private String title;
}