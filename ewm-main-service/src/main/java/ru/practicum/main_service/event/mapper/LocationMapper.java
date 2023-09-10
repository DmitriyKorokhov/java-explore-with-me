package ru.practicum.main_service.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main_service.event.dto.LocationDto;
import ru.practicum.main_service.event.model.Location;

@UtilityClass
public class LocationMapper {
    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lon(locationDto.getLon())
                .lat(locationDto.getLat())
                .build();
    }

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

}