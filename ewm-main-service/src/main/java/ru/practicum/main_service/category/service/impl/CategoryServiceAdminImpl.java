package ru.practicum.main_service.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.dto.NewCategoryDto;
import ru.practicum.main_service.category.mapper.CategoryMapper;
import ru.practicum.main_service.category.service.CategoryServiceAdmin;
import ru.practicum.main_service.category.service.CategoryServicePublic;
import ru.practicum.main_service.category.storage.CategoryRepository;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.exception.ConflictException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceAdminImpl implements CategoryServiceAdmin {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryServicePublic categoryServicePublic;

    @Override
    @Transactional
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        if (categoryRepository.findByName(newCategoryDto.getName()) != null) {
            throw new ConflictException("A category with that name already exists");
        }
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.newCategoryDtoToCategory(newCategoryDto)));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        categoryServicePublic.getCategoryById(catId);
        if (categoryRepository.findByName(categoryDto.getName(), catId) != null) {
            throw new ConflictException("A category with that name already exists");
        }
        categoryDto.setId(catId);
        return CategoryMapper.toCategoryDto(CategoryMapper.categoryDtoToCategory(categoryDto));
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        categoryServicePublic.getCategoryById(catId);
        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            throw new ConflictException("Error when deleting a category. The category contains events");
        }
        categoryRepository.deleteById(catId);
    }
}