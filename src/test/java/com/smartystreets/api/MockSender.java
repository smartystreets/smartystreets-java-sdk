package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockSender implements Sender {
    private int maxTimeOut = 10000;
    private int sendCount = 0;
    private String validResponse = "[{\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}}]\n";
    private String includeInvalidResponse = "[{\"input_id\":\"address 1\",\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\",\"footnotes\":\"B#N#\"}},{\"input_index\":3,\"candidate_index\":0,\"delivery_line_1\":\"150 Park Road\",\"last_line\":\"Gotham City NY\",\"components\":{\"city_name\":\"Gotham City\",\"state_abbreviation\":\"NY\"},\"metadata\":{\"precision\":\"Unknown\"},\"analysis\":{\"dpv_footnotes\":\"A1\",\"footnotes\":\"C#\"}}]";

    public Response send(Request request) throws SmartyException, IOException {
        Response response = new Response();

        response.setStatusCode(200);
        response.setStatus("OK");

        sendCount++;

        if (request.getUrlString().contains("ServiceUnavailable")) {
            throw new IOException("503 - Service unavailable");
        }

        if (request.getHeaders().containsKey("X-Include-Invalid")) {
            response.setRawJSON(this.includeInvalidResponse);
        }
        else {
            response.setRawJSON(this.validResponse);
        }

        if (request.getUrlString().contains("RetryThreeTimes")) {
            if (sendCount <= 3) {
                throw new IOException("You need to retry");
            }
        }

        if (request.getUrlString().contains("RetryMaxTimes")) {
            throw new IOException("Retrying won't help");
        }

        return response;
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public void resetSendCount() {
        this.sendCount = 0;
    }
}
