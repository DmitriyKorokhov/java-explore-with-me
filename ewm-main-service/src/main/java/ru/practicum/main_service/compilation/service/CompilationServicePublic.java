package ru.practicum.main_service.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationServicePublic {
    CompilationDto getCompilationById(Long compId);

    List<CompilationDto> getAllCompilations(Boolean pinned, Pageable pageable);
}
