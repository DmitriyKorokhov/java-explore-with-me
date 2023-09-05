package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    private String email;
    private Long id;
    private String name;
}