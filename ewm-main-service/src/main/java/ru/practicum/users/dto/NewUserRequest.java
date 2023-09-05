package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class NewUserRequest { //Данные нового пользователя
    @NotBlank(message = "Email не должно быть пустым")
    @Size(max = 254, min = 6, message = "Максимальное кол-во символов для почтового адреса - 254, минимальное - 6")
    private String email;
    @Size(max = 250, min = 2, message = "Максимальное кол-во символов для имени - 250, минимальное - 2")
    @NotBlank(message = "Name не должно быть пустым")
    private String name;
}