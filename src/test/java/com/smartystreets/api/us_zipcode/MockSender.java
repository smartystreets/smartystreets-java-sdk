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
import java.util.HashMap;
import java.util.Map;

public class MockSender implements Sender {
    private final int MAX_TIMEOUT = 10000;
    private int sendCount = 0;
    private final int STATUS_CODE = 200;
    private final String SINGLE_RESPONSE = "[{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Salt Lake City\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]}]";
    private final String BATCH_RESPONSE = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"county_fips\":\"11001\",\"default_city\":\"Washington\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Salt Lake City\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";
    private String responseJson = "";

    public Response send(Request request) throws SmartyException, IOException {
        byte[] payload;

        this.sendCount++;

        if (request.getUrl().contains("ServiceUnavailable")) {
            throw new IOException("503 - Service unavailable");
        }

        if (request.getMethod().equals("GET")) {
            payload = this.SINGLE_RESPONSE.getBytes();
            this.responseJson = this.SINGLE_RESPONSE;
        } else {
            payload = this.BATCH_RESPONSE.getBytes();
            this.responseJson = this.BATCH_RESPONSE;
        }

        if (request.getUrl().contains("RetryThreeTimes")) {
            if (this.sendCount <= 3) {
                throw new IOException("You need to retry");
            }
        }

        if (request.getUrl().contains("RetryMaxTimes")) {
            throw new IOException("Retrying won't help");
        }

        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {

                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(STATUS_CODE);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent(responseJson);
                        return response;
                    }
                };
            }
        };

        HttpRequest innerRequest = transport.createRequestFactory().buildPostRequest(HttpTesting.SIMPLE_GENERIC_URL, null);
        innerRequest.setParser(new JacksonFactory().createJsonObjectParser());
        HttpResponse httpResponse = innerRequest.execute();

        Map<String, String> responseHeaders = new HashMap<>();
        HttpHeaders httpHeaders = httpResponse.getHeaders();
        for (String headerName : httpHeaders.keySet()) {
            responseHeaders.put(headerName, (String) httpHeaders.get(headerName));
        }

        return new Response(this.STATUS_CODE, payload);
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public void resetSendCount() {
        this.sendCount = 0;
    }
}
