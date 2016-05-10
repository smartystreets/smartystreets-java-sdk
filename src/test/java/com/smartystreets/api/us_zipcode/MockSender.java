package com.smartystreets.api.us_zipcode;

import com.google.api.client.http.*;
import com.google.api.client.json.Json;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockSender implements Sender {
    private int maxTimeOut = 10000;
    private int sendCount = 0;
    private int statusCode = 200;
    private String batchResponse = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"county_fips\":\"11001\",\"default_city\":\"Washington\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Salt Lake City\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";
    private String singleResponse = "[{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Salt Lake City\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]}]";
    private String responseJson = "";

    public Response send(Request request) throws SmartyException, IOException {
        Response response = new Response();
        response.setStatusCode(this.statusCode);
        response.setStatus("OK");

        this.sendCount++;

        if (request.getUrlString().contains("ServiceUnavailable")) {
            throw new IOException("503 - Service unavailable");
        }

        if (request.getInnerRequest().getHeaders().containsKey("Content-Type"))
            System.out.println("Content-Type: " + request.getInnerRequest().getHeaders().get("Content-Type"));

        if (request.getMethod().equals("GET")) {
            response.setRawJSON(this.singleResponse);
            this.responseJson = this.singleResponse;
        }
        else if (request.getMethod().equals("POST")) {
            response.setRawJSON(this.batchResponse);
            this.responseJson = this.batchResponse;
        }

        if (request.getUrlString().contains("RetryThreeTimes")) {
            if (this.sendCount <= 3) {
                throw new IOException("You need to retry");
            }
        }

        if (request.getUrlString().contains("RetryMaxTimes")) {
            throw new IOException("Retrying won't help");
        }

        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {

                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(statusCode);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent(responseJson);
                        return response;
                    }
                };
            }
        };

        HttpRequest innerRequest = transport.createRequestFactory().buildPostRequest(HttpTesting.SIMPLE_GENERIC_URL, null);
        innerRequest.setParser(new JacksonFactory().createJsonObjectParser());
        HttpResponse innerResponse = innerRequest.execute();
        response.setInnerResponse(innerResponse);

        return response;
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public void resetSendCount() {
        this.sendCount = 0;
    }
}
