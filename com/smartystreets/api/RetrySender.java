package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyStreetsException;

import java.io.IOException;

public class RetrySender implements Sender {
    Sender inner;
    int maxRetries;
    int remainingRetries;

    public RetrySender(int maxRetries, Sender inner) {
        this.inner = inner;
        this.maxRetries = maxRetries;
        this.remainingRetries = maxRetries;
    }

    public Response send(Request request) throws SmartyStreetsException, IOException {
        try {
            Response response = this.inner.send(request);
            remainingRetries = maxRetries;
            return response;
        }
        catch (IOException ex) {
            if (remainingRetries <= 0) {
                remainingRetries = maxRetries;
                throw ex;
            }
            else {
                remainingRetries--;
                this.send(request);
            }
        }

        return null;
    }

    public int getMaxTimeOut() {
        return 0;
    }

    public void setMaxTimeOut(int maxTimeOut) {

    }
}
