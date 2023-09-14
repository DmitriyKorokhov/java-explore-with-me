package ru.practicum.main_service.event.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStats {
    private Long eventId;
    private Long confirmedRequests;
}