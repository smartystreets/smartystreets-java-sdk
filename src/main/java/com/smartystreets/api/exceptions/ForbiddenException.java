package com.smartystreets.api.exceptions;

public class ForbiddenException extends SmartyException{
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
