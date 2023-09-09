package ru.practicum.main_service.compilation.service;

import ru.practicum.main_service.compilation.dto.CompilationDto;
import ru.practicum.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.main_service.compilation.dto.UpdateCompilationRequest;

public interface CompilationServiceAdmin {
    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    CompilationDto updateCompilationById(Long compId, UpdateCompilationRequest updateCompilationRequest);

    void deleteCompilationById(Long compId);
}