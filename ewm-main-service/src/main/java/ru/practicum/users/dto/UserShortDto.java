package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserShortDto {
    public Long id;
    public String name;
}