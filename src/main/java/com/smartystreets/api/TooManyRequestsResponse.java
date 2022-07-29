package com.smartystreets.api;

import java.net.http.HttpHeaders;

public class TooManyRequestsResponse extends Response {

    private HttpHeaders headers;

    public TooManyRequestsResponse(HttpHeaders headers, int statusCode, byte[] payload) {
        super(statusCode, payload);
        this.headers = headers;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
