package ru.practicum.events.compilation.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class NewCompilationDto { // Подборка событий
    private List<Long> events;
    private boolean pinned; // Закреплена ли подборка на главной странице сайта example: true
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для заголовка подборки - 50, минимальное - 1")
    @NotBlank(message = "Title не может быть пустым")
    private String title; // Заголовок подборки example: Летние концерты
}