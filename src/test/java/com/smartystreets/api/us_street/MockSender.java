package com.smartystreets.api.us_street;

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
    private final String VALID_RESPONSE = "[{\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}}]\n";
    private final String INCLUDE_INVALID_RESPONSE = "[{\"input_id\":\"address 1\",\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\",\"footnotes\":\"B#N#\"}},{\"input_index\":3,\"candidate_index\":0,\"delivery_line_1\":\"150 Park Road\",\"last_line\":\"Gotham City NY\",\"components\":{\"city_name\":\"Gotham City\",\"state_abbreviation\":\"NY\"},\"metadata\":{\"precision\":\"Unknown\"},\"analysis\":{\"dpv_footnotes\":\"A1\",\"footnotes\":\"C#\"}}]";
    private String responseJson = "";

    public Response send(Request request) throws SmartyException, IOException {
        byte[] payload; // = new byte[INCLUDE_INVALID_RESPONSE.length()*2]

        this.sendCount++;

        if (request.getUrlString().contains("ServiceUnavailable")) {
            throw new IOException("503 - Service unavailable");
        }

        if (request.getHeaders().containsKey("X-Include-Invalid")) {
            payload = this.INCLUDE_INVALID_RESPONSE.getBytes();
            this.responseJson = this.INCLUDE_INVALID_RESPONSE;
        }
        else {
            payload = this.VALID_RESPONSE.getBytes();
            this.responseJson = this.VALID_RESPONSE;
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

        return new Response(this.STATUS_CODE, responseHeaders, payload);
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public void resetSendCount() {
        this.sendCount = 0;
    }
}
