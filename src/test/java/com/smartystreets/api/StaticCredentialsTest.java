package com.smartystreets.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StaticCredentialsTest {
    @Test
    public void testStandardCredentials() throws Exception {
        assertSignedRequest("f83280df-s83d-f82j-d829-kd02l9tis7ek", "S9Djk63k2Ilj67vN82Km",
                "https://api.smartystreets.com/street-address?auth-id=f83280df-s83d-f82j-d829-kd02l9tis7ek&auth-token=S9Djk63k2Ilj67vN82Km");
    }

    @Test
    public void testUrlEncoding() {
        assertSignedRequest("as3$d8+56d9", "d8j#ds'dfe2",
                "https://api.smartystreets.com/street-address?auth-id=as3%24d8%2B56d9&auth-token=d8j%23ds%27dfe2");
    }

    private static void assertSignedRequest(String id, String secret, String expected) {
        StaticCredentials credentials = new StaticCredentials(id, secret);
        Request request = new Request("https://api.smartystreets.com/street-address?");
        credentials.sign(request);
        assertEquals(expected, request.getUrl());

    }
}