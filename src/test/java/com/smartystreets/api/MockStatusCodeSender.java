package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockStatusCodeSender implements Sender {

    private final int statusCode;

    public MockStatusCodeSender(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public Response send(Request request) throws SmartyException, IOException {
        if (this.statusCode == 0)
            return null;

        return new Response(this.statusCode, null);
    }
}
