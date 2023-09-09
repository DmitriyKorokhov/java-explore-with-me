package ru.practicum.main_service.event.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    @NotNull(message = "Latitude должен существовать")
    private Float lat;
    @NotNull(message = "Longitude должен существовать")
    private Float lon;
}