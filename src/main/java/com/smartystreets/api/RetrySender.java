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

    public Response send(Request request) throws SmartyException, IOException, InterruptedException {
        for (int i = 0; i <= this.maxRetries; i++) {
            Response response = this.trySend(request, i);
            if (response instanceof TooManyRequestsResponse) {
                long wait = ((TooManyRequestsResponse) response).getHeaders().firstValueAsLong("Retry-After").orElse(10L);
                if (wait < 1) {
                    wait = 1L;
                }
                //this.logger.log("The rate limit for requests has been exceeded. Sleeping " + wait + " seconds...");
                this.sleeper.sleep(wait);
                i = 0;
                response = null;
            }

            if (response != null) {
                return response;
            }
        }
        return null;
    }

    private Response trySend(Request request, int attempt) throws SmartyException, IOException, InterruptedException {
        try {
            return this.inner.send(request);
        } catch (IOException ex) {
            if (attempt >= this.maxRetries)
                throw ex;
        }

        this.backoff(attempt);

        return null;
    }

    private void backoff(int attempt) throws InterruptedException {
        long backoffDuration = Math.min(attempt, MAX_BACKOFF_DURATION);

        this.logger.log("There was an error processing the request. Retrying in "+backoffDuration+" seconds...");
        this.sleeper.sleep(backoffDuration);

    }

}
