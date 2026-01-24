package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class RetrySender implements Sender {
    public final int MAX_BACKOFF_DURATION = 10;
    private long maxWaitTime;
    private Sender inner;
    private int maxRetries;
    private Sleeper sleeper;
    private Logger logger;

    public RetrySender(int maxRetries, Sleeper sleeper, Logger logger, Sender inner) {
        this.maxRetries = maxRetries;
        this.maxWaitTime = 0;
        this.sleeper = sleeper;
        this.logger = logger;
        this.inner = inner;
    }

    public RetrySender WithMaxWaitTime(long maxWaitTime)  {
        this.maxWaitTime = maxWaitTime;
        return this;
    }

    public Response send(Request request) throws SmartyException, IOException, InterruptedException {
        int totalWaitTime = 0;
        for (int i = 0; i <= this.maxRetries; i++) {
            Response response = this.trySend(request, i);
            if (response instanceof TooManyRequestsResponse) {
                 long wait = 10L;
                 String retryAfter = ((TooManyRequestsResponse) response).getHeaders().get("Retry-After");
                if (retryAfter != null && retryAfter.length() > 0) {
                    wait = Long.parseLong(retryAfter);
                }
                if (wait < 1) {
                    wait = 1L;
                }
                totalWaitTime += wait;
                if (maxWaitTime > 0 && totalWaitTime > maxWaitTime) {
                    throw new SmartyException("In RetrySender, the wait time is longer than the maxWaitTime.");
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

    @Override
    public void close() throws IOException {
        this.inner.close();
    }
}
