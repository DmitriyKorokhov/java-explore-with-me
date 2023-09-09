package ru.practicum.main_service.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    private List<Long> events = new ArrayList<>();
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для заголовка подборки - 50, минимальное - 1")
    @NotBlank(message = "Title не может быть пустым")
    private String title;
    private Boolean pinned = false;
}