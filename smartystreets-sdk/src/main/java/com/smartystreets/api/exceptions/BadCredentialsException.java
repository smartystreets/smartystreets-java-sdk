package com.smartystreets.api.exceptions;

public class BadCredentialsException extends SmartyException {
    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
