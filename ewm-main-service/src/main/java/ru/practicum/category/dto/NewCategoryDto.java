package ru.practicum.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto { // Данные для добавления новой категории
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для названия категории - 50, минимальное - 1")
    @NotBlank(message = "Поле name не должно быть пустым")
    private String name; // Название категории example: Концерты
}
