package com.smartystreets.api.exceptions;

public class RequestTimeoutException extends SmartyException{
    public RequestTimeoutException() {
        super();
    }

    public RequestTimeoutException(String message) {
        super(message);
    }
}
