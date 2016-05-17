package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockSender implements Sender {

    private final Response response;
    private Request request;

    public MockSender(Response response) {
        this.response = response;
    }

    @Override
    public Response send(Request request) throws SmartyException, IOException {
        this.request = request;
        return this.response;
    }

    public Request getRequest() {
        return this.request;
    }
}
