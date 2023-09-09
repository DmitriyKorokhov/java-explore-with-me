package ru.practicum.main_service.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.mapper.CategoryMapper;
import ru.practicum.main_service.category.service.CategoryServicePublic;
import ru.practicum.main_service.parameters.EwmPageRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
@Slf4j
public class CategoryControllerPublic {

    private final CategoryServicePublic categoryServicePublic;

    @GetMapping
    public List<CategoryDto> getAllCategory(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Запрос на поиск всех категорий");
        return categoryServicePublic.getAllCategory(EwmPageRequest.of(from, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.info("Запрос на поиск категории по id= {}", catId);
        return CategoryMapper.toCategoryDto(categoryServicePublic.getCategoryById(catId));
    }
}