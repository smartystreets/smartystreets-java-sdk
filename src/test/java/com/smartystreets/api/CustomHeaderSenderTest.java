package com.smartystreets.api;

import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomHeaderSenderTest {

    @Test
    public void testAllCustomHeadersAreAddedToTheRequest() throws Exception {
        HashMap<String, CustomHeader> headers = new HashMap<>();
        headers.put("A", new CustomHeader("1"));
        headers.put("B", new CustomHeader("2"));
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, inner);
        Request request = new Request();
        sender.send(request);

        Map<String, Object> requestHeaders = inner.getRequest().getHeaders();
        assertNotNull("There should be headers here.", requestHeaders);
        assertEquals("1", inner.getRequest().getHeaders().get("A"));
    }

    @Test
    public void testMultipleAppendedHeadersAccumulate() throws Exception {
        HashMap<String, CustomHeader> headers = new HashMap<>();
        headers.put("User-Agent", new CustomHeader("a" + " " + "b", " "));
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, inner);
        Request request = new Request();
        request.putHeader("User-Agent", "base");
        sender.send(request);

        assertEquals("base a b", inner.getRequest().getHeaders().get("User-Agent"));
    }

    @Test
    public void testAppendedHeadersAreJoinedWithSeparator() throws Exception {
        HashMap<String, CustomHeader> headers = new HashMap<>();
        headers.put("User-Agent", new CustomHeader("custom-value", " "));
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, inner);
        Request request = new Request();
        request.putHeader("User-Agent", "base-value");
        sender.send(request);

        Map<String, Object> requestHeaders = inner.getRequest().getHeaders();
        assertNotNull("There should be headers here.", requestHeaders);
        assertEquals("base-value custom-value", requestHeaders.get("User-Agent"));
    }
}
