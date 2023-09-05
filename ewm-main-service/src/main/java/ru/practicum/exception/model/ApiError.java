package ru.practicum.exception.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError { // Сведения об ошибке
    private List<String> errors; //Список стектрейсов или описания ошибок
    private String message; //Сообщение об ошибке
    private String reason; // Общее описание причины ошибки
    private String status; // example: FORBIDDEN Код статуса HTTP-ответа
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp; // Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
}