package ru.practicum.category.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class CategoryDto { // Категория
    private Long id; // Идентификатор категории
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для названии категории - 50, минимальное - 1")
    @NotBlank(message = "Поле name не должно быть пустым")
    private String name; // example: Концерты Название категории
}
