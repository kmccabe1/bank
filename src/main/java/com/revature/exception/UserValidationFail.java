package com.revature.exception;

public class UserValidationFail extends RuntimeException {
    public UserValidationFail(String message) {
        super(message);
    }
}
