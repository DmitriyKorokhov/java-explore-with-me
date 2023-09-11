package ru.practicum.main_service.compilation.dto;

import lombok.*;
import ru.practicum.main_service.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    @Size(max = 50, min = 1, message = "The maximum number of characters for the title is 50, the minimum is 1")
    @NotBlank(message = "The Title can not be empty")
    private String title;
}