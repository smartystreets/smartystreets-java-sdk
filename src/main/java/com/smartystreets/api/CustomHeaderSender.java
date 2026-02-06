package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.Map;

public class CustomHeaderSender implements Sender {
    private Map<String, Object> headers;
    private Map<String, String> appendHeaders;
    private Sender inner;

    public CustomHeaderSender(Map<String, Object> headers, Map<String, String> appendHeaders, Sender inner){
        this.headers = headers;
        this.appendHeaders = appendHeaders;
        this.inner = inner;
    }

    @Override
    public Response send(Request request) throws SmartyException, IOException, InterruptedException {
        for (Map.Entry entry : this.headers.entrySet()) {
            String key = (String) entry.getKey();
            if (this.appendHeaders != null && this.appendHeaders.containsKey(key)) {
                String separator = this.appendHeaders.get(key);
                request.appendHeader(key, entry.getValue(), separator);
            } else {
                request.putHeader(key, entry.getValue());
            }
        }

        return this.inner.send(request);
    }

    @Override
    public void close() throws IOException {
        this.inner.close();
    }
}
