package com.example.librarymanagement.exception;

import java.util.function.Supplier;

public class NoPatronFoundException extends RuntimeException {
    public NoPatronFoundException(String message) {
        super(message);
    }
}
