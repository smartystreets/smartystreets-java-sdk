package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.Map;

public class CustomHeaderSender implements Sender {
    private Map<String, String> headers;
    private Sender inner;

    public CustomHeaderSender(Map<String, String> headers, Sender inner){
        this.headers = headers;
        this.inner = inner;
    }

    @Override
    public Response send(Request request) throws SmartyException, IOException {
        Map<String, String> requestHeaders = request.getHeaders();

        for (Map.Entry entry : this.headers.entrySet()) {
            request.putHeader((String)entry.getKey(), (String)entry.getValue());
        }

        return this.inner.send(request);
    }
}
