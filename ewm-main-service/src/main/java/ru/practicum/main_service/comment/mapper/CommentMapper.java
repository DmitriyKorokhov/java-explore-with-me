package ru.practicum.main_service.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.comment.dto.NewCommentDto;
import ru.practicum.main_service.comment.dto.ResponseCommentDto;
import ru.practicum.main_service.comment.model.Comment;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.user.dto.UserShortDto;
import ru.practicum.main_service.user.mapper.UserMapper;
import ru.practicum.main_service.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "user")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "text", expression = "java(newCommentDto.getText())")
    @Mapping(target = "created", expression = "java(convertToLocation())")
    @Mapping(target = "status", constant = "UNDER_CONSIDERATION")
    Comment toComment(User user, Event event, NewCommentDto newCommentDto);

    @Mapping(target = "eventId", source = "comment.event.id")
    @Mapping(target = "author", expression = "java(convertToUserShortDto(comment.getAuthor()))")
    ResponseCommentDto toResponseCommentDto(Comment comment);

    List<ResponseCommentDto> toResponseCommentsDto(List<Comment> comments);

    default UserShortDto convertToUserShortDto(User user) {
        return UserMapper.INSTANCE.toUserShortDto(user);
    }

    default LocalDateTime convertToLocation() {
        return LocalDateTime.now();
    }
}