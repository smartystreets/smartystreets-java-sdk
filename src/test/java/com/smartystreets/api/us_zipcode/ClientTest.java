package com.smartystreets.api.us_zipcode;

import com.google.api.client.http.*;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.Json;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.smartystreets.api.Request;
import org.apache.http.HttpResponseFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ClientTest {
    private Batch batch;
    private Lookup lookup1;
    private Lookup lookup2;
    private Lookup lookup3;
    private String expectedJsonPayload;
    private String expectedJsonResponse;
    Request request;

    @Before
    public void setData() throws Exception {
        this.batch = new Batch();
        this.lookup1 = new Lookup("Provo", "Utah", "84606");
        this.lookup2 = new Lookup("Salt Lake City", "Utah").setInputId("test id");
        this.lookup3 = new Lookup("54321");
        this.batch.add(lookup1);
        this.batch.add(lookup2);
        this.batch.add(lookup3);
        this.expectedJsonPayload = "[{\"city\":\"Provo\",\"state\":\"Utah\",\"zipcode\":\"84606\"},{\"city\":\"Salt Lake City\",\"input_id\":\"test id\",\"state\":\"Utah\"},{\"zipcode\":\"54321\"}]";
        this.expectedJsonResponse = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"county_fips\":\"11001\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Provo\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";
        this.request = new Request();
        this.request.setUrlString("https://api.smartystreets.com/street-address?");
    }

    @Test
    public void testSerializeIntoRequestUrl() throws Exception {
        Client.serializeIntoRequestUrl(this.batch, this.request);

        assertEquals("https://api.smartystreets.com/street-address?city=Provo&state=Utah&zipcode=84606", request.getUrlString());
    }

    @Test
    public void testSerializeIntoRequestBody() throws Exception {
        Client.serializeIntoRequestBody(this.batch, this.request);

        assertEquals(this.expectedJsonPayload, this.request.getJsonPayload());
    }

    @Test
    public void testDeserializeResponse() throws Exception {
        /**Setup*/
        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {

                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent(expectedJsonResponse);
                        return response;
                    }
                };
            }
        };
        HttpRequest request = transport.createRequestFactory().buildPostRequest(HttpTesting.SIMPLE_GENERIC_URL, null);
        request.setParser(new JacksonFactory().createJsonObjectParser());
        HttpResponse response = request.execute();

        Client.deserializeResponse(this.batch, response);
        Result result1 = this.batch.get(0).getResult();
        Result result2 = this.batch.get(1).getResult();
        Result result3 = this.batch.get(2).getResult();

        /**Analysis*/
        assertNotNull(result1);
        assertEquals(0, result1.getInputIndex());
        assertNotNull(result1.getCityState(0));
        assertEquals("Washington", result1.getCityState(0).getCity());
        assertEquals("20500", result1.getZipCode(0).getZipcode());

        assertNotNull(result2);
        assertNull(result2.getStatus());
        assertEquals("test id", result2.getInputId());
        assertEquals("Utah", result2.getCityState(0).getState());
        assertEquals(38.89769, result2.getZipCode(0).getLatitude(), .00001);

        assertNotNull(result3);
        assertNull(result3.getCityStates());
        assertEquals("invalid_zipcode", result3.getStatus());
        assertEquals("Invalid ZIP Code.", result3.getReason());
    }
}