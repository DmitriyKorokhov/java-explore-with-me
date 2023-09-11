package ru.practicum.main_service.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    private long id;
    @NotBlank(message = "The Name should not be empty")
    @Size(max = 250, min = 2, message = "The maximum number of characters for a Name is 250, the minimum is 2")
    private String name;
}