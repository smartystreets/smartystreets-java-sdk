package com.smartystreets.api.exceptions;

public class MissingAuthTokenOnPOSTException extends SmartyStreetsException {
    public MissingAuthTokenOnPOSTException() {
        super();
    }

    public MissingAuthTokenOnPOSTException(String message) {
        super(message);
    }
}
