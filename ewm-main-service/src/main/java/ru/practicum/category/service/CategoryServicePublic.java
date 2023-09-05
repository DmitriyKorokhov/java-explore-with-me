package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryServicePublic {
    List<CategoryDto> getAllCategory(int from, int size);

    CategoryDto getCategoryById(Long catId);
}
