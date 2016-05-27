package com.smartystreets.api.mocks;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class RequestCapturingSender implements Sender {
    private Request request;

    @Override
    public Response send(Request request) throws SmartyException, IOException {
        this.request = request;

        return new Response(200, "[]".getBytes());
    }

    public Request getRequest() {
        return this.request;
    }
}
