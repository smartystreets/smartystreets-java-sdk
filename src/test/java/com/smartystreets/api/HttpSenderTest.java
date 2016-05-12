package com.smartystreets.api;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.smartystreets.api.exceptions.BadRequestException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HttpSenderTest {
    @Test
    public void testSend() throws Exception {
        HttpSender sender = new HttpSender();

        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {

                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent("This is the response.");
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
                        response.setContent("Bad request test.");
                        return response;
                    }
                };
            }
        };

        /**Case 1: Test GET with custom header*/
        HttpRequest innerRequest = transport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
        innerRequest.setParser(new JacksonFactory().createJsonObjectParser());
        Request request = new Request();
        request.setInnerRequest(innerRequest);
        request.setMethod("GET");
        request.addHeader("X-Include-Invalid","true");

        Response response = sender.send(request);

        assertNotNull(response);
        assertNotNull(response.getInnerResponse());
        assertEquals("true", innerRequest.getHeaders().get("X-Include-Invalid"));
        assertEquals("This is the response.", response.getInnerResponse().parseAsString());

        /**Case 2: Test POST*/
        innerRequest = transport.createRequestFactory().buildPostRequest(HttpTesting.SIMPLE_GENERIC_URL, null);
        innerRequest.getHeaders().setContentType(Json.MEDIA_TYPE);
        request.setMethod("POST");

        response = sender.send(request);

        assertNotNull(response);
        assertNotNull(response.getInnerResponse());
        assertEquals("This is the response.", response.getInnerResponse().parseAsString());
        assertEquals("application/json; charset=UTF-8", innerRequest.getHeaders().getContentType());

        /**Case 3: Test handling error codes*/
        innerRequest = errorTransport.createRequestFactory().buildPostRequest(HttpTesting.SIMPLE_GENERIC_URL, null);
        request.setInnerRequest(innerRequest);

        boolean threwException = false;
        try {
            sender.send(request);
        }
        catch (BadRequestException ex) {
            threwException = true;
        }
        finally {
            assertTrue(threwException);
        }
    }
}