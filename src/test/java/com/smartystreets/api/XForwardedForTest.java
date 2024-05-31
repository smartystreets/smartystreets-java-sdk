package com.smartystreets.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.smartystreets.api.mocks.RequestCapturingSender;

public class XForwardedForTest {
    //In order to create a functioning 
    @Test
    public void testAllCustomHeadersAreAddedToTheRequest() throws Exception {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("X-Forwarded-For", "ip");
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, inner);
        Request request = new Request();
        
        sender.send(request);

        Map<String, Object> requestHeaders = inner.getRequest().getHeaders();
        assertNotNull("Headers here.", requestHeaders);
        assertEquals(headers.get("X-Forwarded-For"), inner.getRequest().getHeaders().get("X-Forwarded-For"));
        
    }
}
