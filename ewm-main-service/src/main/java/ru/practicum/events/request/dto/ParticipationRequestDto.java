package ru.practicum.events.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ParticipationRequestDto { // Заявка на участие в событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime created; // 2022-09-06T21:10:05.432 Дата и время создания заявки
    private Long event; // Идентификатор события
    private Long id; //Идентификатор заявки
    private Long requester; // Идентификатор пользователя, отправившего заявку
    private String status; // example: PENDING Статус заявки
}