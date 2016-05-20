package com.smartystreets.api.us_street.mocks;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockSender implements Sender {

    private final byte[] bytes;

    public MockSender(byte[] bytes) {
        this.bytes = bytes;
    }

    public Response send(Request request) throws SmartyException, IOException {
        return new Response(0, this.bytes);
    }
}
