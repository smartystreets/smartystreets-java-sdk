package com.smartystreets.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EmbeddedKeyCredentialsTest {
    @Test
    public void assertSignedRequest() {
        Request request = this.createSignedRequest();
        String expected = "https://us-street.api.smartystreets.com/street-address?key=3516378604772256";

        assertEquals("Request param 'key' must be specified", expected, request.getUrl());
	    assertNull("Request header 'Referer' must not be specified", request.getHeaders().get("Referer"));
    }

    private Request createSignedRequest() {
        Credentials credentials = new EmbeddedKeyCredentials("3516378604772256");
        Request request = new Request();
        request.setUrlPrefix("https://us-street.api.smartystreets.com/street-address");
        credentials.sign(request);
        return request;
    }
}