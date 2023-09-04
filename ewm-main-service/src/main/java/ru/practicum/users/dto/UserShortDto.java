package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserShortDto {
    private Long id;
    private String name;
}
