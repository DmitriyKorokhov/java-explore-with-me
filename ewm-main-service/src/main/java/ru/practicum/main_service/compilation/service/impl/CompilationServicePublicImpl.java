package ru.practicum.main_service.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.compilation.dto.CompilationDto;
import ru.practicum.main_service.compilation.mapper.CompilationMapper;
import ru.practicum.main_service.compilation.model.Compilation;
import ru.practicum.main_service.compilation.service.CompilationServicePublic;
import ru.practicum.main_service.compilation.storage.CompilationRepository;
import ru.practicum.main_service.event.dto.EventShortDto;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.service.EventServicePrivate;
import ru.practicum.main_service.exception.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServicePublicImpl implements CompilationServicePublic {

    private final EventServicePrivate eventServicePrivate;
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = getCompilation(compId);
        List<EventShortDto> eventsShortDto = eventServicePrivate.toEventsShortDto(compilation.getEvents());
        return CompilationMapper.toCompilationDto(compilation, eventsShortDto);
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Pageable pageable) {
        List<Compilation> compilations = findCompilations(pinned, pageable);
        Map<Long, EventShortDto> eventsShortDto = getEventsShortDto(compilations);
        return compilations.stream()
                .map(compilation -> {
                    List<EventShortDto> compEventsShortDto = new ArrayList<>();
                    compilation.getEvents().forEach(event -> compEventsShortDto.add(eventsShortDto.get(event.getId())));
                    return CompilationMapper.toCompilationDto(compilation, compEventsShortDto);
                }).collect(Collectors.toList());
    }

    private List<Compilation> findCompilations(Boolean pinned, Pageable pageable) {
        if (pinned == null) {
            return compilationRepository.findAll(pageable).toList();
        } else {
            return compilationRepository.findAllByPinned(pinned, pageable);
        }
    }

    private Map<Long, EventShortDto> getEventsShortDto(List<Compilation> compilations) {
        Set<Event> uniqueEvents = new HashSet<>();
        compilations.forEach(compilation -> uniqueEvents.addAll(compilation.getEvents()));
        Map<Long, EventShortDto> eventsShortDto = new HashMap<>();
        eventServicePrivate.toEventsShortDto(new ArrayList<>(uniqueEvents)).forEach(event -> eventsShortDto.put(event.getId(), event));
        return eventsShortDto;
    }

    private Compilation getCompilation(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Resource not found"));
    }
}