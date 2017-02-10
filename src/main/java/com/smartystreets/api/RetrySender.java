package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class RetrySender implements Sender {
    public final int MAX_BACKOFF_DURATION = 10;
    private Sender inner;
    private int maxRetries;
    private Sleeper sleeper;
    private Logger logger;

    public RetrySender(int maxRetries, Sleeper sleeper, Logger logger, Sender inner) {
        this.maxRetries = maxRetries;
        this.sleeper = sleeper;
        this.logger = logger;
        this.inner = inner;
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
        } catch (IOException ex) {
            if (attempt >= this.maxRetries)
                throw ex;
        }

        this.backoff(attempt);

        return null;
    }

    private void backoff(int attempt) {
        long backoffDuration = Math.min(attempt, MAX_BACKOFF_DURATION);

        this.logger.log("There was an error processing the request. Retrying in "+backoffDuration+" seconds...");

        try {
            this.sleeper.sleep(backoffDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
