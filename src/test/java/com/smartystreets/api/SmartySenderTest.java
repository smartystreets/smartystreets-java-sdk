package com.smartystreets.api;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmartySenderTest {

    @Test
    public void testHttpRequestContainsCorrectHeaders() throws Exception {
        SmartySender sender = new SmartySender(mockHttpClient("{\"key\": \"value\"}", 200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");
        request.putHeader("X-name1", "value1");
        request.putHeader("X-name2", "value2");

        sender.send(request);

        Map<String, Object> headers = request.getHeaders();
        assertEquals("value1", headers.get("X-name1"));
        assertEquals("value2", headers.get("X-name2"));
    }

    @Test
    public void testHttpRequestContainsGetWhenAppropriate() throws Exception {
        String responseString = "This is a GET response.";
        SmartySender sender = new SmartySender(mockHttpClient(responseString, 200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertArrayEquals(responseString.getBytes(), response.getPayload());
    }

    @Test
    public void testHttpRequestContainsPostWhenAppropriate() throws Exception {
        String responseString = "This is a POST response.";
        SmartySender sender = new SmartySender(mockHttpClient(responseString, 200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        request.setPayload(new byte[0]);
        Response response = sender.send(request);

        assertArrayEquals(responseString.getBytes(), response.getPayload());
    }

//    @Test
//    public void testHttpRequestContainsCorrectContent() throws Exception {
//        SmartySender sender = new SmartySender(this.getMockClient(200));
//        Request request = new Request();
//        request.setUrlPrefix("http://localhost");
//
//        request.setPayload("This is the test content.".getBytes());
//        sender.send(request);
//
//        assertEquals("This is the test content.", this.httpRequest.bodyPublisher().toString());
//    }

    //endregion

    //region [ Response Packaging ]

    @Test
    public void testResponseContainsCorrectPayload() throws Exception {
        String responseBody = "{\"key\": \"value\"}";
        SmartySender sender = new SmartySender(mockHttpClient(responseBody, 200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertArrayEquals(responseBody.getBytes(), response.getPayload());
    }

    @Test
    public void testResponseContainsStatusCode200OnSuccess() throws Exception {
        String responseBody = "{\"key\": \"value\"}";
        SmartySender sender = new SmartySender(mockHttpClient(responseBody, 200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testResponseContainsStatusCode400WhenA400IsThrown() throws Exception {
        String responseBody = "{\"key\": \"value\"}";
        SmartySender sender = new SmartySender(mockHttpClient(responseBody, 400));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertEquals(400, response.getStatusCode());
    }

    private static OkHttpClient mockHttpClient(final String serializedBody, final int code) throws IOException {
        final OkHttpClient okHttpClient = mock(OkHttpClient.class);

        final Call remoteCall = mock(Call.class);

        final okhttp3.Response response = new okhttp3.Response.Builder()
                .request(new okhttp3.Request.Builder().url("http://url.com").build())
                .protocol(Protocol.HTTP_2)
                .code(code).message("").body(
                        ResponseBody.create(
                                serializedBody,
                                MediaType.parse("application/json")
                        ))
                .build();

        when(remoteCall.execute()).thenReturn(response);
        when(okHttpClient.newCall(any())).thenReturn(remoteCall);

        return okHttpClient;
    }
}