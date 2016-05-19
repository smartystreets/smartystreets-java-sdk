package com.smartystreets.api.mocks;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockCrashingSender implements Sender {
    private int sendCount = 0;
    private final int STATUS_CODE = 200;


    @Override
    public Response send(Request request) throws SmartyException, IOException {
        this.sendCount++;

        if (request.getUrl().contains("RetryThreeTimes")) {
            if (this.sendCount <= 3) {
                throw new IOException("You need to retry");
            }
        }

        if (request.getUrl().contains("RetryMaxTimes")) {
            throw new IOException("Retrying won't help");
        }

        return new Response(this.STATUS_CODE, new byte[]{});
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public void resetSendCount() {
        this.sendCount = 0;
    }
}
