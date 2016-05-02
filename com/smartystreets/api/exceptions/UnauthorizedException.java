package com.smartystreets.api.exceptions;

public class UnauthorizedException extends SmartyStreetsException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
