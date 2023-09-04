package ru.practicum.events.event.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LocationDto {
    private float lat; // example: 55.754167 - Широта
    private float lon; // example: 37.62 - Долгота
}