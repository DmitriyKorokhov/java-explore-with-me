package ru.practicum.main_service.exception.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main_service.exception.ConflictException;
import ru.practicum.main_service.exception.model.ApiError;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(RuntimeException exception) {
        return new ApiError("CONFLICT", exception.getMessage(), HttpStatus.CONFLICT.toString(), LocalDateTime.now());
    }

}