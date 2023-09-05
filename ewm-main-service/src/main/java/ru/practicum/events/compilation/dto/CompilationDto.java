package ru.practicum.events.compilation.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.events.event.dto.EventShortDto;

import java.util.List;

@Value
@Builder
public class CompilationDto { //Подборка событий
    private List<EventShortDto> events;
    private Long id;
    private boolean pinned; // Закреплена ли подборка на главной странице сайта example: true
    private String title; // Заголовок подборки example: Летние концерты
}