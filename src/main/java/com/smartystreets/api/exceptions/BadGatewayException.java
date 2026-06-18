package com.smartystreets.api.exceptions;

public class BadGatewayException extends SmartyException{
    public BadGatewayException() {
        super();
    }

    public BadGatewayException(String message) {
        super(message);
    }
}
