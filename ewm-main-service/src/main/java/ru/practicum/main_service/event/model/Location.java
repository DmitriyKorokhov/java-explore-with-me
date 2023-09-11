package ru.practicum.main_service.event.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations", schema = "public")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lat")
    @NotNull(message = "Latitude must exist")
    private Float lat;
    @Column(name = "lon")
    @NotNull(message = "Longitude must exist")
    private Float lon;
}