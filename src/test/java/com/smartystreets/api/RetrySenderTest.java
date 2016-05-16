package com.smartystreets.api;

import com.smartystreets.api.us_street.MockSender;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RetrySenderTest {
    @Test
    public void testSend() throws Exception {
        /**Case 1: Make sure it doesn't retry if there are no exceptions*/
        Request request = new Request("DoNotRetry");
        MockSender inner = new MockSender();
        RetrySender retrySender = new RetrySender(5, inner);

        retrySender.send(request);

        assertEquals(1, inner.getSendCount());

        /**Case 2: Retries until no exception is thrown - doesn't max out*/
        request = new Request("RetryThreeTimes");
        inner.resetSendCount();

        retrySender.send(request);

        assertEquals(4, inner.getSendCount());

        /**Case 3: Retry the max number of tries before throwing the exception*/
        request = new Request("RetryMaxTimes");
        inner.resetSendCount();

        String exMessage = "";
        try {
            retrySender.send(request);
        }
        catch (IOException ex) {
            exMessage = ex.getMessage();
        }
        finally {
            assertEquals("Retrying won't help", exMessage);
            assertEquals(6, inner.getSendCount());
        }
    }

}