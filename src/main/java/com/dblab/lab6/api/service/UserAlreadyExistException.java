package com.dblab.lab6.api.service;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String signUpErrorField, String fieldValue) {
        super(String.format("User with %s '%s' already exists", signUpErrorField, fieldValue));
    }
}
