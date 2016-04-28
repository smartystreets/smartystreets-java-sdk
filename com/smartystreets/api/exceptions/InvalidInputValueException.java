package com.smartystreets.api.exceptions;

/**
 * Created by oshion on 4/28/16.
 */
public class InvalidInputValueException extends Exception {
    public InvalidInputValueException() {
        super();
    }

    public InvalidInputValueException(String message) {
        super(message);
    }
}
