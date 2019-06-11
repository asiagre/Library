package com.project.library.controller;

public class WrongIdException extends RuntimeException {
    public WrongIdException(String message) {
        super(message);
    }
}
