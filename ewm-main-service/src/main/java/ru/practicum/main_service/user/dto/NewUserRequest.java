package ru.practicum.main_service.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
    @NotBlank(message = "Email не должно быть пустым")
    @Size(max = 250, min = 2, message = "Максимальное кол-во символов для почтового адреса - 250, минимальное - 2")
    private String name;
    @Email(message = "Email должен соответсвовать верному форм фактору")
    @Size(max = 254, min = 6, message = "Максимальное кол-во символов для имени - 254, минимальное - 6")
    @NotBlank(message = "Name не должно быть пустым")
    private String email;
}