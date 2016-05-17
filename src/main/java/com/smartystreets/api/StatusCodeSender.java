package com.smartystreets.api;

import com.smartystreets.api.exceptions.BadCredentialsException;
import com.smartystreets.api.exceptions.PaymentRequiredException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.exceptions.TooManyRequestsException;

import java.io.IOException;

public class StatusCodeSender implements Sender {

    private final Sender inner;

    public StatusCodeSender(Sender inner) {
        this.inner = inner;
    }

    public Response send(Request request) throws SmartyException, IOException {
        Response response = this.inner.send(request);

        switch (response.getStatusCode()) {
            case 401:
                throw new BadCredentialsException();
            case 402:
                throw new PaymentRequiredException();
            case 429:
                throw new TooManyRequestsException();

            default:
                return response;
        }
    }
}
