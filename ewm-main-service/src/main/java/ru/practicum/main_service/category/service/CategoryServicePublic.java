package ru.practicum.main_service.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.model.Category;

import java.util.List;

public interface CategoryServicePublic {
    Category getCategoryById(Long catId);

    List<CategoryDto> getAllCategory(Pageable pageable);
}
