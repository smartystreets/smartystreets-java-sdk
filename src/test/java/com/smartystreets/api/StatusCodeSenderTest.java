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
    public void test500ResponseThrowsInternalServerErrorException() throws Exception {
        this.assertSend(500, InternalServerErrorException.class);
    }

    @Test
    public void test503ResponseThrowsServiceUnavailableException() throws Exception {
        this.assertSend(503, ServiceUnavailableException.class);
    }

    @Test
    public void test304CarriesResponseEtag() {
        Headers headers = new Headers.Builder().add("Etag", "server-refreshed-etag").build();
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(304, null, headers)));

        NotModifiedException ex = assertThrows(NotModifiedException.class, () -> sender.send(new Request()));
        assertEquals("server-refreshed-etag", ex.getResponseEtag());
    }

    @Test
    public void test304ResponseEtagCaseInsensitive() {
        Headers headers = new Headers.Builder().add("ETag", "case-insensitive-etag").build();
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(304, null, headers)));

        NotModifiedException ex = assertThrows(NotModifiedException.class, () -> sender.send(new Request()));
        assertEquals("case-insensitive-etag", ex.getResponseEtag());
    }

    @Test
    public void test304ResponseEtagNullWhenHeaderAbsent() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(304, null)));

        NotModifiedException ex = assertThrows(NotModifiedException.class, () -> sender.send(new Request()));
        assertNull(ex.getResponseEtag());
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
    public void test422FallsBackWhenBodyEmpty() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, new byte[0])));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields.", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenBodyNull() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, null)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields.", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenBodyMalformed() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, "not json".getBytes(StandardCharsets.UTF_8))));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields.", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenErrorsArrayMissing() {
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, "{\"other\":\"shape\"}".getBytes(StandardCharsets.UTF_8))));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields.", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenMessageIsNull() {
        byte[] body = "{\"errors\":[{\"message\":null}]}".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, body)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields.", ex.getMessage());
    }

    @Test
    public void test422FallsBackWhenErrorsArrayEmpty() {
        byte[] body = "{\"errors\":[]}".getBytes(StandardCharsets.UTF_8);
        StatusCodeSender sender = new StatusCodeSender(new MockSender(new Response(422, body)));

        UnprocessableEntityException ex = assertThrows(UnprocessableEntityException.class, () -> sender.send(new Request()));
        assertEquals("GET request lacked required fields.", ex.getMessage());
    }

    private void assertSend(int statusCode, Class<? extends Throwable> exceptionType) throws Exception {
        StatusCodeSender sender = new StatusCodeSender(new MockStatusCodeSender(statusCode));

        assertThrows(SmartyException.class, () -> sender.send(new Request()));
    }
}
