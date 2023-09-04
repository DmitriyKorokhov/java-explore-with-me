package ru.practicum.events.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;

@Value
@Builder
public class EventFullDto {
    private String annotation; // example: Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории Краткое описание
    private CategoryDto category;
    private Long confirmedRequests; // Количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn; // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description; //Полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private Long id;
    private UserShortDto initiator; //Пользователь (краткая информация)
    private LocationDto location; //Широта и долгота места проведения события
    private boolean paid; // Нужно ли оплачивать участие
    private int participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn; //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    private String state; // example: PUBLISHED, Список состояний жизненного цикла события
    private String title; // example: Знаменитое шоу 'Летающая кукуруза' Заголовок
    private Long views; // Количество просмотрев события
}