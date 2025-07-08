package com.smartystreets.api;

import com.smartystreets.api.exceptions.*;

import java.io.IOException;

public class StatusCodeSender implements Sender {
    private final Sender inner;

    public StatusCodeSender(Sender inner) {
        this.inner = inner;
    }

    public Response send(Request request) throws SmartyException, IOException, InterruptedException {
        Response response = this.inner.send(request);

        switch (response.getStatusCode()) {
            case 200:
            case 429: // Too Many Requests - Rate Limit reached. We handle this with the response, not a throwable
                return response;
            case 401:
                throw new BadCredentialsException("Unauthorized: The credentials were provided incorrectly or did not match any existing, active credentials.");
            case 304:
                throw new NotModifiedException("Record has not been modified since the last request.");
            case 402:
                throw new PaymentRequiredException("Payment Required: There is no active subscription for the account associated with the credentials submitted with the request.");
            case 403:
                throw new ForbiddenException("Because the international service is currently in a limited release phase, only approved accounts may access the service.");
            case 413:
                throw new RequestEntityTooLargeException("Request Entity Too Large: The request body has exceeded the maximum size.");
            case 400:
                throw new BadRequestException("Bad Request (Malformed Payload): A GET request lacked a street field or the request body of a POST request contained malformed JSON.");
            case 422:
                throw new UnprocessableEntityException("GET request lacked required fields.");
            case 500:
                throw new InternalServerErrorException("Internal Server Error.");
            case 503:
                throw new ServiceUnavailableException("Service Unavailable. Try again later.");
            case 504:
                throw new GatewayTimeoutException("The upstream data provider did not respond in a timely fashion and the request failed. A serious, yet rare occurrence indeed.");
            default:
                return null;
        }
    }
}
