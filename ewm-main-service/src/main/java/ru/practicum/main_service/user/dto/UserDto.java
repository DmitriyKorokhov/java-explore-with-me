package ru.practicum.main_service.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    @NotBlank(message = "The Name should not be empty")
    @Size(max = 250, min = 2, message = "The maximum number of characters for a Name is 250, the minimum is 2")
    private String name;
    @Email(message = "The Email must match the correct format")
    @NotBlank(message = "The Email should not be empty")
    @Size(max = 254, min = 6, message = "The maximum number of characters for Email is 254, the minimum is 6")
    private String email;
}