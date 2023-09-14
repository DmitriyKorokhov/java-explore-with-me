package ru.practicum.main_service.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.category.dto.CategoryDto;

import java.util.List;

public interface CategoryServicePublic {
    CategoryDto getCategoryById(Long catId);

    List<CategoryDto> getAllCategory(Pageable pageable);
}
