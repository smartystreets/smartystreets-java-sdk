package com.smartystreets.api.exceptions;

public class InternalServerErrorException extends SmartyException {
    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
