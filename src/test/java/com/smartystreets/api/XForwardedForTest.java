package com.smartystreets.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.smartystreets.api.mocks.RequestCapturingSender;

public class XForwardedForTest {
    @Test
    public void testAllCustomHeadersAreAddedToTheRequest() throws Exception {
        HashMap<String, CustomHeader> headers = new HashMap<>();
        headers.put("X-Forwarded-For", new CustomHeader("ip"));
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, inner);
        Request request = new Request();

        sender.send(request);

        Map<String, Object> requestHeaders = inner.getRequest().getHeaders();
        assertNotNull("Headers here.", requestHeaders);
        assertEquals("ip", inner.getRequest().getHeaders().get("X-Forwarded-For"));

    }
}
