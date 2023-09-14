package ru.practicum.main_service.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.user.dto.NewUserRequest;
import ru.practicum.main_service.user.dto.UserDto;
import ru.practicum.main_service.user.model.User;

import java.util.List;

public interface UserServiceAdmin {
    UserDto addUser(NewUserRequest newUserRequest);

    User getUser(long id);

    List<UserDto> getAllUsersByIds(List<Long> ids, Pageable pageable);

    void deleteUserById(long id);
}