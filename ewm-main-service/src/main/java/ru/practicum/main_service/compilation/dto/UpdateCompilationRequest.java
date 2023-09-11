package ru.practicum.main_service.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequest {
    private List<Long> events;
    @Size(max = 50, min = 1, message = "The maximum number of characters for the title is 50, the minimum is 1")
    private String title;
    private Boolean pinned;
}