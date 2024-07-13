package com.example.librarymanagement.exception;

public class PatronAlreadyExistsException extends RuntimeException{
    public PatronAlreadyExistsException(String message) {
        super(message);
    }
}
