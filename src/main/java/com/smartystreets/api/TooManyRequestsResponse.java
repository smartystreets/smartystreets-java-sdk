package com.smartystreets.api;

import okhttp3.Headers;

public class TooManyRequestsResponse extends Response {

    private Headers headers;

    public TooManyRequestsResponse(Headers headers, int statusCode, byte[] payload) {
        super(statusCode, payload);
        this.headers = headers;
    }

    public Headers getHeaders() {
        return headers;
    }
}
