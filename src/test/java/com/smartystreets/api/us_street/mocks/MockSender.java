package com.smartystreets.api.us_street.mocks;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class MockSender implements Sender {
    private final int STATUS_CODE = 200;
    private final String VALID_SINGLE_RESPONSE = "[{\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}}]\n";
    private final String VALID_RESPONSE = "[{\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}},{\"input_index\":0,\"candidate_index\":1,\"delivery_line_1\":\"800 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 95543-1354\",\"delivery_point_barcode\":\"940431461001\",\"components\":{\"primary_number\":\"1601\",\"street_name\":\"Amphitheatre Place\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94045\",\"plus4_code\":\"1354\",\"delivery_point\":\"01\",\"delivery_point_check_digit\":\"1\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06087\",\"county_name\":\"Santa Land\",\"carrier_route\":\"C907\",\"congressional_district\":\"16\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0311\",\"elot_sort\":\"A\",\"latitude\":37.22222,\"longitude\":-122.08665,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}},{\"input_index\":1,\"candidate_index\":0,\"addressee\":\"Apple Inc\",\"delivery_line_1\":\"1 Infinite Loop\",\"last_line\":\"Cupertino CA 95014-2083\",\"delivery_point_barcode\":\"950142083017\",\"components\":{\"primary_number\":\"1\",\"street_name\":\"Infinite\",\"street_suffix\":\"Loop\",\"city_name\":\"Cupertino\",\"state_abbreviation\":\"CA\",\"zipcode\":\"95014\",\"plus4_code\":\"2083\",\"delivery_point\":\"01\",\"delivery_point_check_digit\":\"7\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C067\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0031\",\"elot_sort\":\"A\",\"latitude\":37.33053,\"longitude\":-122.02887,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}}]\n";
    private final String INCLUDE_INVALID_RESPONSE = "[{\"input_id\":\"address 1\",\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\",\"footnotes\":\"B#N#\"}},{\"input_index\":1,\"candidate_index\":0,\"delivery_line_1\":\"150 Park Road\",\"last_line\":\"Gotham City NY\",\"components\":{\"city_name\":\"Gotham City\",\"state_abbreviation\":\"NY\"},\"metadata\":{\"precision\":\"Unknown\"},\"analysis\":{\"dpv_footnotes\":\"A1\",\"footnotes\":\"C#\"}},{\"input_index\":2,\"candidate_index\":0,\"addressee\":\"Apple Inc\",\"delivery_line_1\":\"1 Infinite Loop\",\"last_line\":\"Cupertino CA 95014-2083\",\"delivery_point_barcode\":\"950142083017\",\"components\":{\"primary_number\":\"1\",\"street_name\":\"Infinite\",\"street_suffix\":\"Loop\",\"city_name\":\"Cupertino\",\"state_abbreviation\":\"CA\",\"zipcode\":\"95014\",\"plus4_code\":\"2083\",\"delivery_point\":\"01\",\"delivery_point_check_digit\":\"7\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C067\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0031\",\"elot_sort\":\"A\",\"latitude\":37.33053,\"longitude\":-122.02887,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}}]";
    private String responseJson = "";

    public Response send(Request request) throws SmartyException, IOException {
        byte[] payload;

        if (request.getUrl().contains("ServiceUnavailable")) {
            throw new IOException("503 - Service unavailable");
        }

        if (request.getUrl().contains("singleAddressLookup")) {
            payload = this.VALID_SINGLE_RESPONSE.getBytes();
            this.responseJson = this.VALID_SINGLE_RESPONSE;
        }
        else if (request.getHeaders().containsKey("X-Include-Invalid")) {
            payload = this.INCLUDE_INVALID_RESPONSE.getBytes();
            this.responseJson = this.INCLUDE_INVALID_RESPONSE;
        }
        else  {
            payload = this.VALID_RESPONSE.getBytes();
            this.responseJson = this.VALID_RESPONSE;
        }

        return new Response(this.STATUS_CODE, payload);
    }
}
