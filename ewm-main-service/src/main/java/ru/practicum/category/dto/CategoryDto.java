package ru.practicum.category.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class CategoryDto { // Категория
    Long id; // Идентификатор категории
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для названии категории - 50, минимальное - 1")
    @NotBlank(message = "Поле name не должно быть пустым")
    String name; // example: Концерты Название категории

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
