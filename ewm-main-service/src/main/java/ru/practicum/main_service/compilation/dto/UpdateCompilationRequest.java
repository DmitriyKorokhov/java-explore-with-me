package ru.practicum.main_service.compilation.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequest {
    private List<Long> events;
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для заголовка подборки - 50, минимальное - 1")
    private String title;
    private Boolean pinned;
}