package com.smartystreets.api.exceptions;

public class PaymentRequiredException extends SmartyException {
    public PaymentRequiredException() {
        super();
    }

    public PaymentRequiredException(String message) {
        super(message);
    }
}
