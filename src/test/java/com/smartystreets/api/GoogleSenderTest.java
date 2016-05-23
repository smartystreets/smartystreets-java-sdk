package com.smartystreets.api;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.smartystreets.api.exceptions.BadRequestException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GoogleSenderTest {
    @Test
    public void testSend() throws Exception {
        GoogleSender sender = new GoogleSender();

        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(final String method, String url) throws IOException {

                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {

                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);

                        if (method.equals("GET"))
                            response.setContent("This is a GET response.");
                        else
                            response.setContent("This is a POST response.");

                        response.addHeader("X-Test-Header", "Test header");
                        return response;
                    }
                };
            }
        };

        HttpTransport errorTransport = new MockHttpTransport() {
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

        /**Case 1: Test GET*/
        Request request = new Request("https://api.smartystreets.com/street-address?");
        sender.setHttpTransport(transport);

        Response response = sender.send(request);

        assertNotNull(response);
        assertArrayEquals("This is a GET response.".getBytes(), response.getPayload());

        /**Case 2: Test POST*/
        request.setPayload(new byte[]{});

        response = sender.send(request);

        assertNotNull(response);
        assertArrayEquals("This is a POST response.".getBytes(), response.getPayload());

        /**Case 3: Test handling error codes*/
        sender.setHttpTransport(errorTransport);

        response = sender.send(request);

        assertEquals(400, response.getStatusCode());
    }
}