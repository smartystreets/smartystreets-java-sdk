package com.smartystreets.api.mocks;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.TooManyRequestsResponse;
import com.smartystreets.api.exceptions.SmartyException;
import okhttp3.Headers;

import java.io.IOException;

public class MockCrashingSender implements Sender {
    private int sendCount = 0;
    private final static int STATUS_CODE = 200;

    @Override
    public Response send(Request request) throws SmartyException, IOException {
        this.sendCount++;

        Response response = new Response(STATUS_CODE, new byte[]{});

        if (request.getUrl().contains("TooManyRequests")) {
            if (this.sendCount <= 2) {
                response = new TooManyRequestsResponse(Headers.of("Retry-After", "7"), STATUS_CODE, new byte[]{});
            }
        }

        if (request.getUrl().contains("RetryThreeTimes")) {
            if (this.sendCount <= 3) {
                throw new IOException("You need to retry");
            }
        }

        if (request.getUrl().contains("RetryMaxTimes")) {
            throw new IOException("Retrying won't help");
        }

        if (request.getUrl().contains("RetryFifteenTimes") ) {
            if (this.sendCount <= 14)
                throw new IOException("You need to retry");
        }

        if (request.getUrl().contains("WaitTimeTooLong") ) {
            response = new TooManyRequestsResponse(Headers.of("Retry-After", "4"), STATUS_CODE, new byte[]{});
        }

        return response;
    }

    public int getSendCount() {
        return this.sendCount;
    }
}
