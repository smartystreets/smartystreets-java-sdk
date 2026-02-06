package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.Map;

public class CustomHeaderSender implements Sender {
    private Map<String, CustomHeader> headers;
    private Sender inner;

    public CustomHeaderSender(Map<String, CustomHeader> headers, Sender inner){
        this.headers = headers;
        this.inner = inner;
    }

    @Override
    public Response send(Request request) throws SmartyException, IOException, InterruptedException {
        for (Map.Entry<String, CustomHeader> entry : this.headers.entrySet()) {
            CustomHeader header = entry.getValue();
            if (header.isAppend()) {
                request.appendHeader(entry.getKey(), header.getValue(), header.getSeparator());
            } else {
                request.putHeader(entry.getKey(), header.getValue());
            }
        }

        return this.inner.send(request);
    }

    @Override
    public void close() throws IOException {
        this.inner.close();
    }
}
