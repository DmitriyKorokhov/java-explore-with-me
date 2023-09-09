package ru.practicum.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.main_service.event.model.EventStateAction;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000, message = "Минимальное кол-во символов для новой аннотации - 20, максимальное - 2000")
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = "Минимальное кол-во символов для нового описания - 20, максимальное - 7000")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
    @Valid
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero(message = "ParticipantLimit пользователей не может быть отрицательным")
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventStateAction stateAction;
    @Size(min = 3, max = 120, message = "Минимальное кол-во символов для нового заголовка - 3, максимальное - 120")
    private String title;
}