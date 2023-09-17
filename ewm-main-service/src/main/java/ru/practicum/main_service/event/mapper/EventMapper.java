package ru.practicum.main_service.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.mapper.CategoryMapper;
import ru.practicum.main_service.event.dto.EventFullDto;
import ru.practicum.main_service.event.dto.EventShortDto;
import ru.practicum.main_service.event.dto.LocationDto;
import ru.practicum.main_service.event.dto.NewEventDto;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.model.EventState;
import ru.practicum.main_service.event.model.Location;
import ru.practicum.main_service.user.dto.UserShortDto;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.mapper.UserMapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class, LocationMapper.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "createdOn", source = "createdOn")
    Event toEvent(NewEventDto newEventDto, User initiator, Category category, Location location, LocalDateTime createdOn, EventState state);

    @Mapping(target = "eventShortDto", expression = "java(toEventShortDto(event, confirmedRequests, views))")
    @Mapping(target = "location", expression = "java(convertToLocationDto(event.getLocation()))")
    EventFullDto toEventFullDto(Event event, Long confirmedRequests, Long views);

    @Mapping(target = "confirmedRequests", source = "confirmedRequests")
    @Mapping(target = "views", source = "views")
    @Mapping(target = "category", expression = "java(convertToCategoryDto(event.getCategory()))")
    @Mapping(target = "initiator", expression = "java(convertToUserShortDto(event.getInitiator()))")
    EventShortDto toEventShortDto(Event event, Long confirmedRequests, Long views);

    default LocationDto convertToLocationDto(Location location) {
        return LocationMapper.INSTANCE.toLocationDto(location);
    }

    default CategoryDto convertToCategoryDto(Category category) {
        return CategoryMapper.INSTANCE.toCategoryDto(category);
    }

    default UserShortDto convertToUserShortDto(User user) {
        return UserMapper.INSTANCE.toUserShortDto(user);
    }
}