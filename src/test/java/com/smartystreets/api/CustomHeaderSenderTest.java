package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.mocks.RequestCapturingSender;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CustomHeaderSenderTest {

    @Test
    public void testAllCustomHeadersAreAddedToTheRequest() throws IOException, SmartyException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("A", "1");
        headers.put("B", "2");
        RequestCapturingSender inner = new RequestCapturingSender();
        CustomHeaderSender sender = new CustomHeaderSender(headers, inner);
        Request request = new Request();

        sender.send(request);

        Map<String, String> requestHeaders = inner.getRequest().getHeaders();
        assertNotNull("There should be headers here.", requestHeaders);
        assertEquals(headers.get("A"), inner.getRequest().getHeaders().get("A"));

    }
}
