package com.smartystreets.api;

import com.smartystreets.api.mocks.MockSender;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SigningSenderTest {

    @Test
    public void testSigningOfRequest() throws Exception {
        StaticCredentials signer = new StaticCredentials("id", "secret");
        MockSender mockSender = new MockSender(null);
        URLPrefixSender urlPrefixSender = new URLPrefixSender("http://localhost/", mockSender);
        SigningSender sender = new SigningSender(signer, urlPrefixSender);

        sender.send(new Request());

        Request request = mockSender.getRequest();
        assertEquals("http://localhost/?auth-id=id&auth-token=secret", request.getUrl());
    }

    @Test
    public void testResponseReturnedCorrectly() throws Exception {
        StaticCredentials signer = new StaticCredentials("id", "secret");
        Response expectedResponse = new Response(200, null);
        MockSender mockSender = new MockSender(expectedResponse);
        URLPrefixSender urlPrefixSender = new URLPrefixSender("http://localhost/", mockSender);
        SigningSender sender = new SigningSender(signer, urlPrefixSender);

        Response actualResponse = sender.send(new Request());

        assertEquals(expectedResponse, actualResponse);
    }
}
