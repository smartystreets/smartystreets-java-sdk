package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.Map;

public class CustomHeaderSender implements Sender {
    private Map<String, Object> headers;
    private Sender inner;

    public CustomHeaderSender(Map<String, Object> headers, Sender inner){
        this.headers = headers;
        this.inner = inner;
    }

    @Override
    public Response send(Request request) throws SmartyException, IOException, InterruptedException {
        for (Map.Entry entry : this.headers.entrySet()) {
            request.putHeader((String)entry.getKey(), entry.getValue());
        }

        return this.inner.send(request);
    }

    @Override
    public void close() throws IOException {
        this.inner.close();
    }
}
