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
    @Size(max = 50, min = 1, message = "The maximum number of characters for the category name is 50, the minimum is 1")
    @NotBlank(message = "The name should not be empty")
    private String name;
}