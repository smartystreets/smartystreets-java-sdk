package com.smartystreets.api;

import com.smartystreets.api.us_street.mocks.MockSender;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RetrySenderTest {

    private MockSender mockSender;

    @Before
    public void setup() {
        this.mockSender = new MockSender();
    }

    @Test
    public void testSuccessDoesNotRetry() throws Exception {
        this.sendRequest("DoNotRetry");

        assertEquals(1, this.mockSender.getSendCount());
    }

    @Test
    public void testRetryUntilSuccess() throws Exception {
        this.sendRequest("RetryThreeTimes");

        assertEquals(4, this.mockSender.getSendCount());
    }

    @Test
    public void testRetryUntilMaxAttempts() throws Exception {
        String message = "";

        try {
            this.sendRequest("RetryMaxTimes");
        } catch (IOException ex) {
            message = ex.getMessage();
        } finally {
            assertEquals("Retrying won't help", message);
            assertEquals(6, this.mockSender.getSendCount());
        }
    }

    private void sendRequest(String requestBehavior) throws Exception {
        Request request = new Request(requestBehavior);
        RetrySender retrySender = new RetrySender(5, this.mockSender);

        retrySender.send(request);
    }
}