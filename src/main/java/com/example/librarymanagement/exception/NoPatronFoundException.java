package com.example.librarymanagement.exception;

public class NoPatronFoundException extends RuntimeException {
    public NoPatronFoundException(String message) {
        super(message);
    }
}
