package com.smartystreets.api.exceptions;

public class InvalidInputValueException extends SmartyException {
    public InvalidInputValueException() {
        super();
    }

    public InvalidInputValueException(String message) {
        super(message);
    }
}
