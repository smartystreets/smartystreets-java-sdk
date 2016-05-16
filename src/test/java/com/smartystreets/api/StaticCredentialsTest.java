package com.smartystreets.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class StaticCredentialsTest {
    @Test
    public void testSign() throws Exception {
        /**Case 1: normal input*/
        StaticCredentials normalCredentials = new StaticCredentials("f83280df-s83d-f82j-d829-kd02l9tis7ek", "S9Djk63k2Ilj67vN82Km");
        Request request = new Request("https://api.smartystreets.com/street-address?");

        normalCredentials.sign(request);
        String urlStringExpected = "https://api.smartystreets.com/street-address?auth-id=f83280df-s83d-f82j-d829-kd02l9tis7ek&auth-token=S9Djk63k2Ilj67vN82Km";
        assertEquals(urlStringExpected, request.getUrlString());

        /**Case 2: test for proper URL encoding*/
        StaticCredentials weirdCredentials = new StaticCredentials("as3$d8+56d9", "d8j#ds'dfe2");
        request = new Request("https://api.smartystreets.com/street-address?");
        weirdCredentials.sign(request);
        urlStringExpected = "https://api.smartystreets.com/street-address?auth-id=as3%24d8%2B56d9&auth-token=d8j%23ds%27dfe2";
        assertEquals(urlStringExpected, request.getUrlString());
    }
}