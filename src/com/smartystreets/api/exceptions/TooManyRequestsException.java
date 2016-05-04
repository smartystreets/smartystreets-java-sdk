package com.smartystreets.api.exceptions;

public class TooManyRequestsException extends SmartyException {
    public TooManyRequestsException() {
        super();
    }

    public TooManyRequestsException(String message) {
        super(message);
    }
}
