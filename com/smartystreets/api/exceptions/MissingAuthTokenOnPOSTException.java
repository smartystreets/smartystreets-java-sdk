package com.smartystreets.api.exceptions;

/**
 * Created by oshion on 4/29/16.
 */
public class MissingAuthTokenOnPOSTException extends Exception {
    public MissingAuthTokenOnPOSTException() {
        super();
    }

    public MissingAuthTokenOnPOSTException(String message) {
        super(message);
    }
}
