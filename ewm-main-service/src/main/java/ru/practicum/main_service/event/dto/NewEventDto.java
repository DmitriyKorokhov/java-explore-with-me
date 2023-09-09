package ru.practicum.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
    @NotBlank(message = "Annotation должно быть заполнено")
    @Size(min = 20, max = 2000, message = "Минимальное кол-во символов для краткого описания - 20, максимальное - 2000")
    private String annotation;
    @NotNull(message = "Category не должно быть пустым")
    private Long category;
    @NotBlank(message = "Description должно быть заполнено")
    @Size(min = 20, max = 7000, message = "Минимальное кол-во символов для полного описания события - 20, максимальное - 7000")
    private String description;
    @NotNull(message = "Event Date должно существовать")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
    @NotNull(message = "Location должно сужествоать")
    private LocationDto location;
    private Boolean paid = false;
    @PositiveOrZero(message = "Participant Limit пользователей не может быть отрицательным")
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;
    @NotBlank(message = "Title должно быть заполнено")
    @Size(min = 3, max = 120, message = "Минимальное кол-во символов для заголовка событий - 3, максимальное - 120")
    private String title;
}