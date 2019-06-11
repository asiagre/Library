package com.project.library.controller;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
