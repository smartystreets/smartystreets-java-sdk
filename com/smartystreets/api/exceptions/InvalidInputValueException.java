package com.smartystreets.api.exceptions;

public class InvalidInputValueException extends SmartyStreetsException {
    public InvalidInputValueException() {
        super();
    }

    public InvalidInputValueException(String message) {
        super(message);
    }
}
