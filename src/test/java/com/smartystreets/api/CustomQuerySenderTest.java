package com.smartystreets.api;

import com.smartystreets.api.mocks.MockSender;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomQuerySenderTest {

    @Test
    public void testAllCustomQueriesAreAddedToTheRequest() throws Exception {
        Request request = new Request();
        HashMap<String, String> queries = new HashMap<>();
        queries.put("query", "1,2,3");
        queries.put("test", "2");
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new CustomQuerySender(queries, inner);

        sender.send(request);

        assertEquals("?test=2&query=1%2C2%2C3", request.getUrl());
    }

    @Test
    public void testNoCustomQueries() throws Exception {
        Request request = new Request();
        HashMap<String, String> queries = new HashMap<>();
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new CustomQuerySender(queries, inner);

        sender.send(request);

        assertEquals("?", request.getUrl());
    }

    @Test
    public void testEmptyCustomQueries() throws Exception {
        Request request = new Request();
        Sender inner = new MockSender(new Response(123, null));
        HashMap<String, String> queries = new HashMap<>();
        queries.put("query", "");
        queries.put("", "test");
        Sender sender = new CustomQuerySender(queries, inner); 
        
        sender.send(request);

        assertEquals("?query=", request.getUrl());
    }

    @Test
    public void testNullCustomQueries() throws Exception {
        Request request = new Request();
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new CustomQuerySender(null, inner); 
        
        sender.send(request);

        assertEquals("?", request.getUrl());
    }
}
