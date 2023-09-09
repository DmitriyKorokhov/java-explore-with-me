package ru.practicum.stats.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidationException extends ResponseStatusException {
    public ValidationException(HttpStatus status, String reason) {
        super(status, reason);
    }
}