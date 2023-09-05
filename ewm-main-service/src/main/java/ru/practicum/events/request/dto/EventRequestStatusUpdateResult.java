package ru.practicum.events.request.dto;

import lombok.*;
import ru.practicum.events.request.dto.ParticipationRequestDto;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult { // Результат подтверждения/отклонения заявок на участие в событии
    private List<ParticipationRequestDto> confirmedRequests; //подтвержденные запросы
    private List<ParticipationRequestDto> rejectedRequests; // отклоненные запросы
}