package ru.practicum.explorewithme.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor
public class StatsDto {
    @NotBlank(message = "App у Stats не должно быть пустым")
    String app;
    @NotBlank(message = "Uri у Stats не должно быть пустым")
    String uri;
    Long hits;
}