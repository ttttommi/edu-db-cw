package com.dblab.lab6.api.service;

public class NoPermissionException extends RuntimeException {

    public NoPermissionException(String permission) {
        super(String.format("User has no permission to %s resources", permission));
    }
}