package com.smartystreets.api;

import com.smartystreets.api.mocks.FakeLogger;
import com.smartystreets.api.mocks.FakeSleeper;
import com.smartystreets.api.mocks.MockCrashingSender;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RetrySenderTest {
    private FakeSleeper fakeSleeper;
    private FakeLogger fakeLogger;

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private MockCrashingSender mockCrashingSender;

    @Before
    public void setup() {
        this.mockCrashingSender = new MockCrashingSender();
        this.fakeSleeper = new FakeSleeper();
        this.fakeLogger = new FakeLogger();
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

    @Test
    public void testBackoffDoesNotExceedMax() throws Exception {
        exception.expect(IOException.class);
        Integer[] expectedDurations = new Integer[] {0,1,2,3,4,5,6,7,8,9,10,10,10,10,10,10,10,10,10,10};


        this.sendRequest("RetryMaxTimes");

        assertArrayEquals(expectedDurations, this.fakeSleeper.getSleepDurations().toArray());
    }

    private void sendRequest(String requestBehavior) throws Exception {
        Request request = new Request();
        request.setUrlPrefix(requestBehavior);
        RetrySender retrySender = new RetrySender(15, this.fakeSleeper, this.fakeLogger, this.mockCrashingSender);

        retrySender.send(request);
    }
}