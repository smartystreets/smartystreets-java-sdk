package com.smartystreets.api;

import com.smartystreets.api.mocks.MockCrashingSender;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RetrySenderTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private MockCrashingSender mockCrashingSender;

    @Before
    public void setup() {
        this.mockCrashingSender = new MockCrashingSender();
    }

    @Test
    public void testSuccessDoesNotRetry() throws Exception {
        this.sendRequest("DoNotRetry");

        assertEquals(1, this.mockCrashingSender.getSendCount());
    }

    @Test
    public void testRetryUntilSuccess() throws Exception {
        this.sendRequest("RetryThreeTimes");

        assertEquals(4, this.mockCrashingSender.getSendCount());
    }

    @Test
    public void testRetryUntilMaxAttempts() throws Exception {
        exception.expect(IOException.class);

        this.sendRequest("RetryMaxTimes");
    }

    private void sendRequest(String requestBehavior) throws Exception {
        Request request = new Request();
        request.setUrlPrefix(requestBehavior);
        RetrySender retrySender = new RetrySender(5, this.mockCrashingSender);

        retrySender.send(request);
    }
}