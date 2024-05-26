package com.dblab.lab6.api.service;

public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException(Long requestId) {
        super(String.format("Request with id %d not found", requestId));
    }
}
