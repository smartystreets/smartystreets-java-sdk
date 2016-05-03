package com.smartystreets.api.exceptions;

public class UnauthorizedException extends SmartyException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
