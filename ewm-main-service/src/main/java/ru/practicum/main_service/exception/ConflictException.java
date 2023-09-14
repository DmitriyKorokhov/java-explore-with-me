package ru.practicum.main_service.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ValidationException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

}