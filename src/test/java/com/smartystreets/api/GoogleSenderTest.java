package com.smartystreets.api;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class GoogleSenderTest {
    private MockLowLevelHttpRequest request;

    //region [ Request Building ]

    @Test
    public void testHttpRequestContainsCorrectHeaders() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockTransport());
        Request request = new Request("http://localhost");
        request.putHeader("X-name1", "value1");
        request.putHeader("X-name2", "value2");

        sender.send(request);

        Map<String, String> headers = request.getHeaders();
        assertEquals("value1", headers.get("X-name1"));
        assertEquals("value2", headers.get("X-name2"));
    }

    @Test
    public void testHttpRequestContainsGetWhenAppropriate() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockTransport());
        Request request = new Request("http://localhost");

        Response response = sender.send(request);

        assertArrayEquals("This is a GET response.".getBytes(), response.getPayload());
    }

    @Test
    public void testHttpRequestContainsPostWhenAppropriate() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockTransport());
        Request request = new Request("http://localhost");

        request.setPayload(new byte[0]);
        Response response = sender.send(request);

        assertArrayEquals("This is a POST response.".getBytes(), response.getPayload());
    }

    @Test
    public void testHttpRequestContainsCorrectContent() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockTransport());
        Request request = new Request("http://localhost");

        request.setPayload("This is the test content.".getBytes());
        sender.send(request);

        assertEquals("This is the test content.", this.request.getContentAsString());
    }

    //endregion

    //region [ Response Packaging ]

    @Test
    public void testResponseContainsCorrectPayload() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockTransport());
        Request request = new Request("http://localhost");

        Response response = sender.send(request);

        assertArrayEquals("This is a GET response.".getBytes(), response.getPayload());
    }

    @Test
    public void testResponseContainsStatusCode200OnSuccess() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockTransport());
        Request request = new Request("http://localhost");

        Response response = sender.send(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testResponseContainsStatusCode400WhenA400IsThrown() throws Exception {
        GoogleSender sender = new GoogleSender(this.getErrorTransport());
        Request request = new Request("http://localhost");

        Response response = sender.send(request);

        assertEquals(400, response.getStatusCode());
    }

    //endregion

    //region [ Transports ]

    private HttpTransport getMockTransport() {
        return new MockHttpTransport() {

            @Override
            public LowLevelHttpRequest buildRequest(final String method, String url) throws IOException {

                request = new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {

                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);

                        if (method.equals("GET"))
                            response.setContent("This is a GET response.");
                        else
                            response.setContent("This is a POST response.");

                        return response;
                    }
                };

                return request;
            }
        };
    }

    private HttpTransport getErrorTransport() {
        return new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {

                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(400);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent("Bad request test.");
                        return response;
                    }
                };
            }
        };
    }

    //endregion
}