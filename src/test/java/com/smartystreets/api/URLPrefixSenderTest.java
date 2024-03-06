package com.smartystreets.api;


import com.smartystreets.api.mocks.MockSender;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class URLPrefixSenderTest {

    @Test
    public void testRequestURLPresent() throws Exception {
        Request request = new Request();
        request.setUrlComponents("/jimbo");
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new URLPrefixSender("http://mysite.com/lookup", inner);

        Response response = sender.send(request);

        assertEquals("http://mysite.com/lookup/jimbo?", request.getUrl());       
    }

    @Test
    public void testRequestURLNotPresent() throws Exception {
        Request request = new Request();
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new URLPrefixSender("http://mysite.com/lookup", inner);

        Response response = sender.send(request);

        assertEquals("http://mysite.com/lookup?", request.getUrl());       
    }

    @Test
    public void testMultipleSends() throws Exception {
        Request request = new Request();
        request.setUrlComponents("/jimbo");
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new URLPrefixSender("http://mysite.com/lookup", inner);

        Response response = sender.send(request);
        response = sender.send(request);
        response = sender.send(request);

        assertEquals("http://mysite.com/lookup/jimbo?", request.getUrl());              
    }
}
