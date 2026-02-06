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
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("A", "1");
        headers.put("B", "2");
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, null, inner);
        Request request = new Request();
        sender.send(request);

        Map<String, Object> requestHeaders = inner.getRequest().getHeaders();
        assertNotNull("There should be headers here.", requestHeaders);
        assertEquals(headers.get("A"), inner.getRequest().getHeaders().get("A"));
    }

    @Test
    public void testAppendedHeadersAreJoinedWithSeparator() throws Exception {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("User-Agent", "custom-value");
        Map<String, String> appendHeaders = new HashMap<>();
        appendHeaders.put("User-Agent", " ");
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, appendHeaders, inner);
        Request request = new Request();
        request.putHeader("User-Agent", "base-value");
        sender.send(request);

        Map<String, Object> requestHeaders = inner.getRequest().getHeaders();
        assertNotNull("There should be headers here.", requestHeaders);
        assertEquals("base-value custom-value", requestHeaders.get("User-Agent"));
    }
}
