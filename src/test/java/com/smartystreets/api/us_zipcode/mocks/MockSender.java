package com.smartystreets.api.us_zipcode.mocks;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockSender implements Sender {
    private final int STATUS_CODE = 200;
    private final String SINGLE_RESPONSE = "[{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Salt Lake City\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]}]";
    private final String BATCH_RESPONSE = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"county_fips\":\"11001\",\"default_city\":\"Washington\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Salt Lake City\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";
    private String responseJson = "";

    public Response send(Request request) throws SmartyException, IOException {
        byte[] payload;

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

        return new Response(this.STATUS_CODE, payload);
    }
}
