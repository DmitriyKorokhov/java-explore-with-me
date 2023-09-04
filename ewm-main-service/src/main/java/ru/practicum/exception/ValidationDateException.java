package ru.practicum.exception;

public class ValidationDateException extends RuntimeException {
    public ValidationDateException(String message) {
        super(message);
    }
}
