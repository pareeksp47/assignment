package com.fieldwire.assignment.exception;

public class CreationException extends Exception {
    public CreationException(String message) {
        super(message);
    }

    public CreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
