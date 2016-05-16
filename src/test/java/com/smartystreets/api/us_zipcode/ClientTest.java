package com.smartystreets.api.us_zipcode;

import com.google.api.client.http.*;
import com.google.api.client.json.Json;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.smartystreets.api.GoogleSerializer;
import com.smartystreets.api.Request;
import com.smartystreets.api.Sender;
import com.smartystreets.api.StaticCredentials;
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
        this.lookup1 = new Lookup("Washington", "District of Columbia", "20500");
        this.lookup2 = new Lookup("Provo", "Utah").setInputId("test id");
        this.lookup3 = new Lookup("54321");
        this.batch.add(lookup1);
        this.batch.add(lookup2);
        this.batch.add(lookup3);
        this.expectedJsonPayload = "[{\"city\":\"Washington\",\"state\":\"District of Columbia\",\"zipcode\":\"20500\"},{\"city\":\"Provo\",\"input_id\":\"test id\",\"state\":\"Utah\"},{\"zipcode\":\"54321\"}]";
        this.expectedJsonResponse = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"default_city\":\"Washington\",\"county_fips\":\"11001\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Provo\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";
        this.request = new Request("https://api.smartystreets.com/street-address?");
    }

    @Test
    public void testSend() throws Exception {
        Client client = new ClientBuilder().withSender(new MockSender()).build();

        /**Case 1: Sending 1 lookup*/
        client.send(this.lookup2);

        Result result = this.lookup2.getResult();

        assertNotNull(result);
        assertEquals("test id", result.getInputId());
        assertEquals("Salt Lake City", result.getCityState(0).getCity());

        /**Case 2: Sending a batch of lookups*/
        client.send(this.batch);

        result = this.lookup1.getResult();

        assertNotNull(result);
        assertEquals("District of Columbia", result.getCityState(0).getState());
        assertEquals("Washington", result.getZipCode(0).getDefaultCity());

        result = this.lookup2.getResult();

        assertNotNull(result);
        assertEquals("test id", result.getInputId());
        assertEquals("Salt Lake City", result.getCityState(0).getCity());

        result = this.lookup3.getResult();

        assertNotNull(result);
        assertNull(result.getCityStates());
        assertEquals(2, result.getInputIndex());
        assertEquals("invalid_zipcode", result.getStatus());
        assertEquals("Invalid ZIP Code.", result.getReason());
    }

    @Test
    public void testPopulateQueryString() throws Exception {
        Client client = new ClientBuilder().build();
        client.populateQueryString(this.batch.get(0), this.request);

        assertEquals("https://api.smartystreets.com/street-address?city=Washington&state=District+of+Columbia&zipcode=20500", request.getUrlString());
    }

    @Test
    public void testAssignResultsToLookups() throws Exception {

    }
}