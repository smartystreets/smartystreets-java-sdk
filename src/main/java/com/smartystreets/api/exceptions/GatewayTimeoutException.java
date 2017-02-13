package com.smartystreets.api.exceptions;

public class GatewayTimeoutException extends SmartyException{
    public GatewayTimeoutException() {
        super();
    }

    public GatewayTimeoutException(String message) {
        super(message);
    }
}
