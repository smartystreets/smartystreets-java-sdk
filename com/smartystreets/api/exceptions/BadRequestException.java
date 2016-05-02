package com.smartystreets.api.exceptions;

public class BadRequestException extends SmartyStreetsException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
