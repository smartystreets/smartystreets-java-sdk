package com.smartystreets.api;

import com.smartystreets.api.exceptions.*;

import java.io.IOException;

public class StatusCodeSender implements Sender {

    private final Sender inner;

    public StatusCodeSender(Sender inner) {
        this.inner = inner;
    }

    public Response send(Request request) throws SmartyException, IOException {
        Response response = this.inner.send(request);

        switch (response.getStatusCode()) {
            case 200:
                return response;
            case 401:
                throw new BadCredentialsException();
            case 402:
                throw new PaymentRequiredException();
            case 413:
                throw new RequestEntityTooLargeException();
            case 400:
                throw new BadRequestException();
            case 429:
                throw new TooManyRequestsException();
            case 500:
                throw new InternalServerErrorException();
            case 503:
                throw new ServiceUnavailableException();
            default:
                return null;
        }
    }
}
