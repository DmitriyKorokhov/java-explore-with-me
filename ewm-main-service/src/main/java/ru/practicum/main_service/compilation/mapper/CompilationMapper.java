package ru.practicum.main_service.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main_service.compilation.dto.CompilationDto;
import ru.practicum.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.main_service.compilation.model.Compilation;
import ru.practicum.main_service.event.dto.EventShortDto;
import ru.practicum.main_service.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public Compilation newDtoToCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        List<Long> evnetIds = events.stream().map(Event::getId).collect(Collectors.toList());
        return Compilation.builder()
                .events(events)
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> eventsShortDto) {
        return CompilationDto.builder()
                .events(eventsShortDto)
                .title(compilation.getTitle())
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .build();
    }
}
