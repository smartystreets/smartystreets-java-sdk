package com.smartystreets.api;

import com.smartystreets.api.exceptions.*;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.MockStatusCodeSender;
import okhttp3.Headers;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class StatusCodeSenderTest {

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
    public void test408ResponseThrowsRequestTimeoutException() {
        RequestTimeoutException ex = assertThrows(RequestTimeoutException.class,
                () -> sendStatus(408, null));
        assertEquals("Request timeout error. Body:", ex.getMessage());
    }

    @Test
    public void test408UsesApiErrorMessageWhenPresent() {
        RequestTimeoutException ex = assertThrows(RequestTimeoutException.class,
                () -> sendStatus(408, "{\"errors\":[{\"message\":\"API timeout message\"}]}"));
        assertEquals("API timeout message", ex.getMessage());
    }

    @Test
    public void test500ResponseThrowsInternalServerErrorException() throws Exception {
        this.assertSend(500, InternalServerErrorException.class);
    }

    @Test
    public void test502ResponseThrowsBadGatewayException() {
        BadGatewayException ex = assertThrows(BadGatewayException.class,
                () -> sendStatus(502, null));
        assertEquals("Bad Gateway error. Body:", ex.getMessage());
    }

    @Test
    public void test502UsesApiErrorMessageWhenPresent() {
        BadGatewayException ex = assertThrows(BadGatewayException.class,
                () -> sendStatus(502, "{\"errors\":[{\"message\":\"API bad gateway message\"}]}"));
        assertEquals("API bad gateway message", ex.getMessage());
    }

    @Test
    public void test400FallsBackToStandardMessage() {
        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> sendStatus(400, null));
        assertEquals("Bad Request (Malformed Payload): A GET request lacked a required field or the request body of a POST request contained malformed JSON. Body:", ex.getMessage());
    }

    @Test
    public void testUnexpectedStatusCodeFallsBackToStandardMessage() {
        SmartyException ex = assertThrows(SmartyException.class,
                () -> sendStatus(418, null));
        assertEquals("The server returned an unexpected HTTP status code: 418 Body:", ex.getMessage());
    }

    @Test
    public void testUnexpectedStatusCodeUsesApiErrorMessageWhenPresent() {
        SmartyException ex = assertThrows(SmartyException.class,
                () -> sendStatus(418, "{\"errors\":[{\"message\":\"API teapot message\"}]}"));
        assertEquals("API teapot message", ex.getMessage());
    }

    @Test
    public void test503ResponseThrowsServiceUnavailableException() throws Exception {
        this.assertSend(503, ServiceUnavailableException.class);
    }

    @Test
    public void test304IsNotAnError() throws Exception {
        Headers headers = new Headers.Builder().add("Etag", "server-refreshed-etag").build();
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(304, null, headers)));

        Response response = sender.send(new Request());

        assertEquals(304, response.getStatusCode());
        assertEquals("server-refreshed-etag", response.getEtag());
    }

    @Test
    public void test422UsesApiErrorMessageWhenPresent() {
        byte[] body = "{\"errors\":[{\"message\":\"The country value specified is not supported\"}]}".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, body)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("The country value specified is not supported", ex.getMessage());
    }

    @Test
    public void test400UsesApiErrorMessageWhenPresent() {
        byte[] body = "{\"errors\":[{\"message\":\"street is required\"}]}".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(400, body)));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> sender.send(new Request()));
        assertEquals("street is required", ex.getMessage());
    }

    @Test
    public void testJoinsMultipleApiErrorMessages() {
        byte[] body = "{\"errors\":[{\"message\":\"First problem.\"},{\"message\":\"Second problem.\"}]}".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, body)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("First problem. Second problem.", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenBodyEmpty() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, new byte[0])));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields. Body:", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenBodyNull() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, null)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields. Body:", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenBodyMalformedAndAppendsBody() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, "not json".getBytes(StandardCharsets.UTF_8))));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields. Body: not json", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenErrorsArrayMissingAndAppendsBody() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, "{\"other\":\"shape\"}".getBytes(StandardCharsets.UTF_8))));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields. Body: {\"other\":\"shape\"}", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenMessageIsNullAndAppendsBody() {
        byte[] body = "{\"errors\":[{\"message\":null}]}".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, body)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields. Body: {\"errors\":[{\"message\":null}]}", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenErrorsArrayEmptyAndAppendsBody() {
        byte[] body = "{\"errors\":[]}".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, body)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields. Body: {\"errors\":[]}", ex.getMessage());
    }

    @Test
    public void testWhitespaceOnlyBodyYieldsEmptyBodyLabel() {
        byte[] body = "   \n  ".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, body)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields. Body:", ex.getMessage());
    }

    private void assertSend(int statusCode, Class<? extends Throwable> exceptionType) throws Exception {
        StatusCodeSender sender = new StatusCodeSender(new MockStatusCodeSender(statusCode));

        assertThrows(SmartyException.class, () -> sender.send(new Request()));
    }

    private void sendStatus(int statusCode, String body) throws Exception {
        byte[] payload = body == null ? null : body.getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(statusCode, payload)));
        sender.send(new Request());
    }
}
