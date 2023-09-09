package ru.practicum.main_service.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private long id;
    @Size(max = 50, min = 1, message = "Максимальное кол-во символов для названии категории - 50, минимальное - 1")
    @NotBlank(message = "Поле name не должно быть пустым")
    private String name;
}