package ru.practicum.stats.server.exception;

public class ValidationDateException extends RuntimeException {
    public ValidationDateException(String message) {
        super(message);
    }
}