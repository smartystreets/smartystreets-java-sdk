package com.smartystreets.api.exceptions;

public class RequestEntityTooLargeException extends SmartyException {
    public RequestEntityTooLargeException() {
        super();
    }

    public RequestEntityTooLargeException(String message) {
        super(message);
    }
}
