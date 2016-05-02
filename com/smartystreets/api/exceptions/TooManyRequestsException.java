package com.smartystreets.api.exceptions;

public class TooManyRequestsException extends SmartyStreetsException {
    public TooManyRequestsException() {
        super();
    }

    public TooManyRequestsException(String message) {
        super(message);
    }
}
