package com.smartystreets.api;


import com.smartystreets.api.mocks.MockSender;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class URLPrefixSenderTest {

    @Test
    public void testProvidedURLOverridesRequestURL() throws Exception {
        Request request = new Request();
        request.setUrlPrefix("http://www.google.com/the/path/stays");
        String override = "https://smartystreets.com/the/path/is/ignored?";
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new URLPrefixSender(override, inner);

        Response response = sender.send(request);

        assertEquals(override, request.getUrl());
    }
}
