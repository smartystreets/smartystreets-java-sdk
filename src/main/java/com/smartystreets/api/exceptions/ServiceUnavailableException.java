package com.smartystreets.api.exceptions;

public class ServiceUnavailableException extends SmartyException {
    public ServiceUnavailableException() {
        super();
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }
}

