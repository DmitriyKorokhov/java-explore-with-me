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
    @NotEmpty(message = "The request Ids list should not be empty")
    private List<Long> requestIds;
    @NotNull(message = "The Status must exist")
    private RequestStatusAction status;
}