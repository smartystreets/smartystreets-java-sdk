package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class RetrySender implements Sender {
    Sender inner;
    int maxRetries;

    public RetrySender(int maxRetries, Sender inner) {
        this.inner = inner;
        this.maxRetries = maxRetries;
    }

    public Response send(Request request) throws SmartyException, IOException {
        for (int i = 0; i <= this.maxRetries; i++) {
            Response response = this.trySend(request, i);

            if (response != null) {
                return response;
            }
        }
        return null;
    }

    private Response trySend(Request request, int attempt) throws SmartyException, IOException {
        try {
            return this.inner.send(request);
        }
        catch (IOException ex) {
            if (attempt >= this.maxRetries)
                throw ex;
        }

        return null;
    }

    public int getMaxRetries() {
        return this.maxRetries;
    }

    public Sender getInner() {
        return this.inner;
    }
}
