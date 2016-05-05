package com.smartystreets.api.exceptions;

public class BatchFullException extends Exception {
    public BatchFullException() {
        super();
    }

    public BatchFullException(String message) {
        super(message);
    }
}
