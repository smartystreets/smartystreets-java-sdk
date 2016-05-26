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
                throw new BadCredentialsException("Unauthorized: The credentials were provided incorrectly or did not match any existing, active credentials.");
            case 402:
                throw new PaymentRequiredException("Payment Required: There is no active subscription for the account associated with the credentials submitted with the request.");
            case 413:
                throw new RequestEntityTooLargeException("Request Entity Too Large: The request body has exceeded the maximum size.");
            case 400:
                throw new BadRequestException("Bad Request (Malformed Payload): A GET request lacked a street field or the request body of a POST request contained malformed JSON.");
            case 429:
                throw new TooManyRequestsException("When using public \"website key\" authentication, we restrict the number of requests coming from a given source over too short of a time.");
            case 500:
                throw new InternalServerErrorException("Internal Server Error.");
            case 503:
                throw new ServiceUnavailableException("Service Unavailable. Try again later.");
            default:
                return null;
        }
    }
}
