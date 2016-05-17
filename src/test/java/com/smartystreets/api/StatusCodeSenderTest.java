package com.smartystreets.api;

import com.smartystreets.api.exceptions.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

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
        this.assertSend(401, BadCredentialsException.class);
    }

    @Test
    public void test402ResponsePThrowsPaymentRequiredException() throws Exception {
        this.assertSend(402, PaymentRequiredException.class);
    }

    @Test
    public void test413ResponseThrowsRequestEntityTooLargeException() throws Exception {
        this.assertSend(413, RequestEntityTooLargeException.class);
    }

    @Test
    public void test400ResponseThrowsBadRequestException() throws Exception {
        this.assertSend(400, BadRequestException.class);
    }

    @Test
    public void test429ResponseThrowsTooManyRequestsException() throws Exception {
        this.assertSend(429, TooManyRequestsException.class);
    }

    @Test
    public void test500ResponseThrowsInternalServerErrorException() throws Exception {
        this.assertSend(500, InternalServerErrorException.class);
    }

    @Test
    public void test503ResponseThrowsServiceUnavailableException() throws Exception {
        this.assertSend(503, ServiceUnavailableException.class);
    }

    private void assertSend(int statusCode, Class<? extends Throwable> exceptionType) throws Exception {
        StatusCodeSender sender = new StatusCodeSender(new MockStatusCodeSender(statusCode));
        exception.expect(exceptionType);
        sender.send(new Request());
    }
}