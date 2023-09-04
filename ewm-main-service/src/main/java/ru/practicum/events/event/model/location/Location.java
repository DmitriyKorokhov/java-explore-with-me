package ru.practicum.events.event.model.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private float lat; // example: 55.754167 - Широта
    private float lon; // example: 37.62 - Долгота
}
