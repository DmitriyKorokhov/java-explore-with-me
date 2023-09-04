package ru.practicum.users.service;

import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;

import java.util.List;

public interface UserServiceAdmin {
    List<UserDto> getAllUsersByIds(List<Long> ids, int from, int size);

    UserDto addUser(NewUserRequest newUserRequest);

    void deleteUserById(Long userId);
}
