package ru.practicum.stats.dto.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {
    @NotBlank(message = "The App should not be empty")
    private String app;
    @NotBlank(message = "The Uri should not be empty")
    private String uri;
    private long hits;
}