package com.smartystreets.api.exceptions;

public class RequestEntityTooLargeException extends SmartyStreetsException {
    public RequestEntityTooLargeException() {
        super();
    }

    public RequestEntityTooLargeException(String message) {
        super(message);
    }
}
