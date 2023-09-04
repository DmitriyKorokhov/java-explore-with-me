package ru.practicum.events.event.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;

@Value
@Builder
public class NewEventDto {
    @NotBlank(message = "annotation должно быть заполнено")
    @Size(min = 20, max = 2000, message = "Минимальное кол-во символов для краткого описания - 20, максимальное - 2000")
    private String annotation; //Краткое описание события
    @NotNull(message = "category не должно быть пустым")
    private Long category;
    @NotBlank(message = "description должно быть заполнено")
    @Size(min = 20, max = 7000, message = "Минимальное кол-во символов для полного описания события - 20, максимальное - 7000")
    private String description; //Полное описание события
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "Invalid date format")
    private String eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private LocationDto location; //Широта и долгота места проведения события
    @org.springframework.beans.factory.annotation.Value("false")
    private boolean paid; // Нужно ли оплачивать участие
    @org.springframework.beans.factory.annotation.Value("0")
    @PositiveOrZero
    private Integer participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @org.springframework.beans.factory.annotation.Value("true")
    private boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    @Size(min = 3, max = 120, message = "Минимальное кол-во символов для заголовка событий - 3, максимальное - 120")
    @NotBlank(message = "title должно быть заполнено")
    private String title; // example: Знаменитое шоу 'Летающая кукуруза' Заголовок события
}