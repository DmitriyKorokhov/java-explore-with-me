package ru.practicum.explorewithme.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class StatsDto {
    @NotBlank(message = "App у Stats не должно быть пустым")
    private String app; //ewm-main-service
    @NotBlank(message = "Uri у Stats не должно быть пустым")
    private String uri; // /events/1
    private Long hits; // кол-во
}