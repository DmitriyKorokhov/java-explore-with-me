package ru.practicum.events.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.compilation.dto.CompilationDto;
import ru.practicum.events.compilation.mapper.CompilationMapper;
import ru.practicum.events.compilation.service.CompilationServicePublic;
import ru.practicum.events.compilation.storage.CompilationStorage;
import ru.practicum.findobject.FindObjectInRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServicePublicImpl implements CompilationServicePublic {

    private final CompilationStorage compilationStorage;
    private final FindObjectInRepository findObjectInRepository;

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, int from, int size) {
        log.info("Получен запрос поиск всех подборок событий, по условию закрепления {}", pinned);
        Pageable pageable = PageRequest.of(from, size);
        return compilationStorage.findAllByPinnedIs(pinned, pageable).stream()
                .map(CompilationMapper::compilationToCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        log.info("Получен запрос на поиск подборки событий по id= {}", compId);
        return CompilationMapper.compilationToCompilationDto(findObjectInRepository.getCompilationById(compId));
    }
}