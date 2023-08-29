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
    private String app;
    @NotBlank(message = "Uri у Stats не должно быть пустым")
    private String uri;
    private Long hits;
}