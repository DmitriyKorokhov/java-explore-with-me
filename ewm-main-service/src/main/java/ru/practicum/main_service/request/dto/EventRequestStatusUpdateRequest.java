package ru.practicum.main_service.request.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotEmpty(message = "Список requestIds не должен быть пустым")
    private List<Long> requestIds;
    @NotNull(message = "Status должен существовать")
    private RequestStatusAction status;
}