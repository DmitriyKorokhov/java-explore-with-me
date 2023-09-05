package ru.practicum.events.request.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateRequest { // Изменение статуса запроса на участие в событии текущего пользователя
    private List<Long> requestIds; // Идентификаторы запросов на участие в событии текущего пользователя
    private RequestStatusDto status;
}