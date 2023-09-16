package ru.practicum.main_service.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.compilation.dto.CompilationDto;
import ru.practicum.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.main_service.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main_service.compilation.mapper.CompilationMapper;
import ru.practicum.main_service.compilation.model.Compilation;
import ru.practicum.main_service.compilation.service.CompilationServiceAdmin;
import ru.practicum.main_service.compilation.service.CompilationServicePublic;
import ru.practicum.main_service.compilation.storage.CompilationRepository;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.service.EventServicePrivate;
import ru.practicum.main_service.exception.ValidationException;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceAdminImpl implements CompilationServiceAdmin {

    private final EventServicePrivate eventServicePrivate;
    private final CompilationRepository compilationRepository;
    private final CompilationServicePublic compilationServicePublic;

    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = getEventsFromNewCompilationDto(newCompilationDto);
        Compilation compilation = compilationRepository.save(CompilationMapper.INSTANCE.newDtoToCompilation(newCompilationDto, events));
        return compilationServicePublic.getCompilationById(compilation.getId());
    }

    @Override
    @Transactional
    public CompilationDto updateCompilationById(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = getCompilation(compId);
        if (updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isBlank()) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getEvents() != null) {
            List<Event> events = eventServicePrivate.getEventsByIds(updateCompilationRequest.getEvents());
            checkSize(events, updateCompilationRequest.getEvents());
            compilation.setEvents(events);
        }
        return compilationServicePublic.getCompilationById(compId);
    }

    @Override
    @Transactional
    public void deleteCompilationById(Long compId) {
        getCompilation(compId);
        compilationRepository.deleteById(compId);
    }

    private List<Event> getEventsFromNewCompilationDto(NewCompilationDto newCompilationDto) {
        if (!newCompilationDto.getEvents().isEmpty()) {
            List<Event> events = eventServicePrivate.getEventsByIds(newCompilationDto.getEvents());
            checkSize(events, newCompilationDto.getEvents());
            return events;
        }
        return Collections.emptyList();
    }

    private void checkSize(List<Event> events, List<Long> eventsIdToUpdate) {
        if (events.size() != eventsIdToUpdate.size()) {
            log.error("Incorrect list ratios");
            throw new ValidationException(HttpStatus.NOT_FOUND, "Resource not found");
        }
    }

    private Compilation getCompilation(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> {
                    log.error("The Compilation does not exist");
                    return new ValidationException(HttpStatus.NOT_FOUND, "Resource not found");
                });
    }
}