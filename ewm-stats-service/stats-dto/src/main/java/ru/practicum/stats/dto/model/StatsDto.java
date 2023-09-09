package ru.practicum.stats.dto.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {
    @NotBlank(message = "App у Stats не должно быть пустым")
    private String app;
    @NotBlank(message = "Uri у Stats не должно быть пустым")
    private String uri;
    private long hits;
}