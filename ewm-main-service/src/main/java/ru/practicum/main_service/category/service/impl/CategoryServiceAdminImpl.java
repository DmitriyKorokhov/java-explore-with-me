package ru.practicum.main_service.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.dto.NewCategoryDto;
import ru.practicum.main_service.category.mapper.CategoryMapper;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.service.CategoryServiceAdmin;
import ru.practicum.main_service.category.service.CategoryServicePublic;
import ru.practicum.main_service.category.storage.CategoryRepository;
import ru.practicum.main_service.event.storage.EventRepository;
import ru.practicum.main_service.exception.ConflictException;

@Slf4j
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
            log.error("A Category with that name already exists");
            throw new ConflictException("A Category with that name already exists");
        }
        Category newCategory = CategoryMapper.INSTANCE.newCategoryDtoToCategory(newCategoryDto);
        return CategoryMapper.INSTANCE.toCategoryDto(categoryRepository.save(newCategory));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        categoryServicePublic.getCategoryById(catId);
        if (categoryRepository.findByName(categoryDto.getName(), catId) != null) {
            log.error("A Category with that name already exists");
            throw new ConflictException("A Category with that name already exists");
        }
        categoryDto.setId(catId);
        Category newCategory = CategoryMapper.INSTANCE.categoryDtoToCategory(categoryDto);
        return CategoryMapper.INSTANCE.toCategoryDto(newCategory);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        categoryServicePublic.getCategoryById(catId);
        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            log.error("Error when deleting a Category. The Category contains Events");
            throw new ConflictException("It is not possible to delete a Category because it contains Events");
        }
        categoryRepository.deleteById(catId);
    }
}