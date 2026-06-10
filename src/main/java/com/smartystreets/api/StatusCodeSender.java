package com.smartystreets.api;

import com.smartystreets.api.exceptions.*;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

public class StatusCodeSender implements Sender {
    private static final JsonMapper MAPPER = JsonMapper.builder().build();

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
                throw new BadCredentialsException(messageFrom(response, "Unauthorized: The credentials were provided incorrectly or did not match any existing, active credentials."));
            case 304:
                throw new NotModifiedException(
                        "Not Modified: The requested record has not been modified since the previous request with the Etag value.",
                        response.getEtag());
            case 402:
                throw new PaymentRequiredException(messageFrom(response, "Payment Required: There is no active subscription for the account associated with the credentials submitted with the request."));
            case 403:
                throw new ForbiddenException(messageFrom(response, "Forbidden: The request contained valid data and was understood by the server, but the server is refusing action."));
            case 408:
                throw new RequestTimeoutException(messageFrom(response, "Request timeout error."));
            case 413:
                throw new RequestEntityTooLargeException(messageFrom(response, "Request Entity Too Large: The request body has exceeded the maximum size."));
            case 400:
                throw new BadRequestException(messageFrom(response, "Bad Request (Malformed Payload): A GET request lacked a required field or the request body of a POST request contained malformed JSON."));
            case 422:
                throw new UnprocessableEntityException(messageFrom(response, "GET request lacked required fields."));
            case 500:
                throw new InternalServerErrorException(messageFrom(response, "Internal Server Error."));
            case 502:
                throw new BadGatewayException(messageFrom(response, "Bad Gateway error."));
            case 503:
                throw new ServiceUnavailableException(messageFrom(response, "Service Unavailable. Try again later."));
            case 504:
                throw new GatewayTimeoutException(messageFrom(response, "The upstream data provider did not respond in a timely fashion and the request failed. A serious, yet rare occurrence indeed."));
            default:
                throw new SmartyException(messageFrom(response, "The server returned an unexpected HTTP status code: " + response.getStatusCode()));
        }
    }

    // messageFrom extracts errors[0].message from the API's JSON error body, falling back to the
    // canned message when the body is empty, unparseable, or missing the expected fields.
    private static String messageFrom(Response response, String fallback) {
        byte[] payload = response.getPayload();
        if (payload == null || payload.length == 0) return fallback;
        try {
            JsonNode message = MAPPER.readTree(payload).path("errors").path(0).path("message");
            if (!message.isTextual()) return fallback;
            String text = message.asText();
            return text.isBlank() ? fallback : text;
        } catch (JacksonException ignored) {
            return fallback;
        }
    }

    @Override
    public void close() throws IOException {
        this.inner.close();
    }
}
