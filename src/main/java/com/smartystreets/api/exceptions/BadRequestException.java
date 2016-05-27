package com.smartystreets.api.exceptions;

public class BadRequestException extends SmartyException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
