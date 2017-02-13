package com.smartystreets.api.exceptions;

public class UnprocessableEntityException extends SmartyException{
    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
