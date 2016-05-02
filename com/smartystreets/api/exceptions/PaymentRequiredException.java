package com.smartystreets.api.exceptions;

public class PaymentRequiredException extends SmartyStreetsException {
    public PaymentRequiredException() {
        super();
    }

    public PaymentRequiredException(String message) {
        super(message);
    }
}
