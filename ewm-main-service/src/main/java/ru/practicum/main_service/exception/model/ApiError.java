package ru.practicum.main_service.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

import static ru.practicum.main_service.parameters.Constants.DATE_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
    private String reason;
    private String status;
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime timestamp;
}