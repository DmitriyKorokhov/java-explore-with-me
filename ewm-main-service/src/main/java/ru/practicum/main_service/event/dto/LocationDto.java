package ru.practicum.main_service.event.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    @NotNull(message = "Latitude must exist")
    private Float lat;
    @NotNull(message = "Longitude must exist")
    private Float lon;
}