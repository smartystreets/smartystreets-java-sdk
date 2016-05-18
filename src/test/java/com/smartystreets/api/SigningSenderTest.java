package com.smartystreets.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SigningSenderTest {

    @Test
    public void testSigningOfRequest() throws Exception {
        StaticCredentials signer = new StaticCredentials("id", "secret");
        MockSender mockSender = new MockSender(null);
        SigningSender sender = new SigningSender(signer, mockSender);

        sender.send(new Request("http://localhost/?")); // TODO: don't add ? here...

        Request request = mockSender.getRequest();
        assertEquals("http://localhost/?auth-id=id&auth-token=secret", request.getUrl());
    }

    @Test
    public void testResponseReturnedCorrectly() throws Exception {
        StaticCredentials signer = new StaticCredentials("id", "secret");
        Response expectedResponse = new Response(200, null);
        MockSender mockSender = new MockSender(expectedResponse);
        SigningSender sender = new SigningSender(signer, mockSender);

        Response actualResponse = sender.send(new Request("http://localhost/"));

        assertEquals(expectedResponse, actualResponse);
    }
}
