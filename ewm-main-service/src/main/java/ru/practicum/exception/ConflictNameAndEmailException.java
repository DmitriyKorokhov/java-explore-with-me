package ru.practicum.exception;

public class ConflictNameAndEmailException extends RuntimeException {
    public ConflictNameAndEmailException(String message) {
        super(message);
    }
}
