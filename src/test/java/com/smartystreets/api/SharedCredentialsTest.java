package com.smartystreets.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class SharedCredentialsTest {
    @Test
    public void assertSignedRequest() {
        Request request = this.createSignedRequest();
        String expected = "https://api.smartystreets.com/street-address?auth-id=3516378604772256";

        assertEquals(expected, request.getUrl());
    }

    @Test public void assertReferringHeader() {
        Request request = this.createSignedRequest();

        assertEquals("https://example.com", request.getHeaders().get("Referer"));
    }

    private Request createSignedRequest() {
        Credentials mobile = new SharedCredentials("3516378604772256", "example.com");
        Request request = new Request("https://api.smartystreets.com/street-address?");
        mobile.sign(request);
        return request;
    }
}