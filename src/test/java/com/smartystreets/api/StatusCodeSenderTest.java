package com.smartystreets.api;

import com.smartystreets.api.exceptions.BadCredentialsException;
import com.smartystreets.api.exceptions.PaymentRequiredException;
import com.smartystreets.api.exceptions.TooManyRequestsException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class StatusCodeSenderTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void test200Response() throws Exception {
        StatusCodeSender sender = new StatusCodeSender(new MockStatusCodeSender(200));

        Response response = sender.send(new Request());

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void test401ResponseThrowsBadCredentialsException() throws Exception {
        StatusCodeSender sender = new StatusCodeSender(new MockStatusCodeSender(401));

        exception.expect(BadCredentialsException.class);
        sender.send(new Request());
    }

    @Test
    public void test402ResponsePaymentRequiredException() throws Exception {
        StatusCodeSender sender = new StatusCodeSender(new MockStatusCodeSender(402));

        exception.expect(PaymentRequiredException.class);
        sender.send(new Request());
    }

    @Test
    public void test429ResponseTooManyRequestsException() throws Exception {
        StatusCodeSender sender = new StatusCodeSender(new MockStatusCodeSender(429));

        exception.expect(TooManyRequestsException.class);
        sender.send(new Request());
    }
}