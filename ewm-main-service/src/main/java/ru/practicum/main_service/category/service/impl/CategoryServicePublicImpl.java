package ru.practicum.main_service.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.mapper.CategoryMapper;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.service.CategoryServicePublic;
import ru.practicum.main_service.category.storage.CategoryRepository;
import ru.practicum.main_service.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServicePublicImpl implements CategoryServicePublic {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "Ресурс не найден"));
    }

    @Override
    public List<CategoryDto> getAllCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}