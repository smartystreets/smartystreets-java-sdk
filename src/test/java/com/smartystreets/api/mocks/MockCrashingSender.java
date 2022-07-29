package com.smartystreets.api.mocks;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.TooManyRequestsResponse;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

public class MockCrashingSender implements Sender {
    private int sendCount = 0;
    private final static int STATUS_CODE = 200;

    @Override
    public Response send(Request request) throws SmartyException, IOException {
        this.sendCount++;

        Response response = new Response(STATUS_CODE, new byte[]{});

        if (request.getUrl().contains("TooManyRequests")) {
            if (this.sendCount <= 2) {
                response = new TooManyRequestsResponse(HttpHeaders.of(Map.of("Retry-After", List.of("7")), (x, y) -> true), STATUS_CODE, new byte[]{});
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

        return response;
    }

    public int getSendCount() {
        return this.sendCount;
    }
}
