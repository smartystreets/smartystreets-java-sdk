package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.Map;

public class CustomQuerySender implements Sender{
    private Map<String, String> queries;
    private Sender inner;

    public CustomQuerySender(Map<String, String> queries, Sender inner){
        this.queries = queries;
        this.inner = inner;
    }

    @Override
    public Response send(Request request) throws SmartyException, IOException, InterruptedException {
        if (this.queries == null || this.queries.isEmpty()) {
            return this.inner.send(request);
        }
        for (Map.Entry<String, String> entry : this.queries.entrySet()) {
            request.putParameter(entry.getKey(), entry.getValue());
        }

        return this.inner.send(request);
    }
}
