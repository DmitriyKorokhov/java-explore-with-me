package ru.practicum.events.event.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.events.event.dto.stateDto.ActionStateDto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Value
@Builder
public class UpdateEventUserRequest { // Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
    @Size(min = 20, max = 2000, message = "Минимальное кол-во символов для новой аннотации - 20, максимальное - 2000")
    private String annotation; // example: Сап прогулки по рекам и каналам – это возможность увидеть Практикбург с другого ракурсаНовая аннотация
    private Long category; // Новая категория
    @Size(min = 20, max = 7000, message = "Минимальное кол-во символов для нового описания - 20, максимальное - 7000")
    private String description; // Новое описание
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "Invalid date format")
    private String eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private LocationDto location; //Широта и долгота места проведения события
    private Boolean paid; // Новое значение флага о платности мероприятия
    @PositiveOrZero(message = "ParticipantLimit пользователей не может быть отрицательным")
    private Integer participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    private ActionStateDto stateAction;
    @Size(min =3, max = 120, message = "Минимальное кол-во символов для нового заголовка - 3, максимальное - 120")
    private String title; // example: Знаменитое шоу 'Летающая кукуруза' Заголовок
}