package com.smartystreets.api.us_street;

import com.smartystreets.api.GoogleSerializer;
import com.smartystreets.api.Request;
import com.smartystreets.api.us_street.mocks.MockSender;
import com.smartystreets.api.us_street.mocks.RequestCapturingSender;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClientTest {
    private Batch batch;
    private AddressLookup lookup1;
    private AddressLookup lookup2;
    private AddressLookup lookup3;
//    private final String expectedJsonPayload = "[{\"city\":\"Washington\",\"state\":\"District of Columbia\",\"zipcode\":\"20500\"},{\"city\":\"Provo\",\"input_id\":\"test id\",\"state\":\"Utah\"},{\"zipcode\":\"54321\"}]";
    private final String expectedJsonResponse = "[{\"input_index\":0,\"candidate_index\":0,\"delivery_line_1\":\"1600 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 94043-1351\",\"delivery_point_barcode\":\"940431351000\",\"components\":{\"primary_number\":\"1600\",\"street_name\":\"Amphitheatre\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain View\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94043\",\"plus4_code\":\"1351\",\"delivery_point\":\"00\",\"delivery_point_check_digit\":\"0\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C909\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0111\",\"elot_sort\":\"A\",\"latitude\":37.42357,\"longitude\":-122.08661,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}},{\"input_index\":0,\"candidate_index\":1,\"delivery_line_1\":\"800 Amphitheatre Pkwy\",\"last_line\":\"Mountain View CA 95543-1354\",\"delivery_point_barcode\":\"940431461001\",\"components\":{\"primary_number\":\"1601\",\"street_name\":\"Amphitheatre Place\",\"street_suffix\":\"Pkwy\",\"city_name\":\"Mountain\",\"state_abbreviation\":\"CA\",\"zipcode\":\"94045\",\"plus4_code\":\"1354\",\"delivery_point\":\"01\",\"delivery_point_check_digit\":\"1\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06087\",\"county_name\":\"Santa Land\",\"carrier_route\":\"C907\",\"congressional_district\":\"16\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0311\",\"elot_sort\":\"A\",\"latitude\":37.22222,\"longitude\":-122.08665,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}},{\"input_index\":1,\"candidate_index\":0,\"addressee\":\"Apple Inc\",\"delivery_line_1\":\"1 Infinite Loop\",\"last_line\":\"Cupertino CA 95014-2083\",\"delivery_point_barcode\":\"950142083017\",\"components\":{\"primary_number\":\"1\",\"street_name\":\"Infinite\",\"street_suffix\":\"Loop\",\"city_name\":\"Cupertino\",\"state_abbreviation\":\"CA\",\"zipcode\":\"95014\",\"plus4_code\":\"2083\",\"delivery_point\":\"01\",\"delivery_point_check_digit\":\"7\"},\"metadata\":{\"record_type\":\"S\",\"zip_type\":\"Standard\",\"county_fips\":\"06085\",\"county_name\":\"Santa Clara\",\"carrier_route\":\"C067\",\"congressional_district\":\"18\",\"rdi\":\"Commercial\",\"elot_sequence\":\"0031\",\"elot_sort\":\"A\",\"latitude\":37.33053,\"longitude\":-122.02887,\"precision\":\"Zip9\",\"time_zone\":\"Pacific\",\"utc_offset\":-8,\"dst\":true},\"analysis\":{\"dpv_match_code\":\"Y\",\"dpv_footnotes\":\"AABB\",\"dpv_cmra\":\"N\",\"dpv_vacant\":\"N\",\"active\":\"Y\"}}]\n";
    Request request;

    @Before
    public void setData() throws Exception {
        this.batch = new Batch();
        this.lookup1 = new AddressLookup("555 N 358 S Provo, Utah 84664");
        this.lookup2 = new AddressLookup("567 N Legit Street Salt Lake City, Utah");
        this.lookup3 = new AddressLookup("54321");
        this.batch.add(lookup1);
        this.batch.add(lookup2);
        this.batch.add(lookup3);
        this.request = new Request("https://api.smartystreets.com/street-address");
    }

    @Test
    public void testSending1AddressLookup() throws Exception {
        Client client = new ClientBuilder().withSender(new MockSender()).withUrl("singleAddressLookup").build();

        client.send(this.lookup1);

        ArrayList<Candidate> result = this.lookup1.getResult();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mountain View", result.get(0).getComponents().getCityName());
    }

    @Test
    public void testSuccessfullySendsBatchOfAddressLookups() throws Exception {
        Client client = new ClientBuilder().withSender(new MockSender()).build();
        client.send(this.batch);

        this.assertFieldsAreCorrect();
    }

    @Test
    public void testCorrectlyPopulatesQueryString() throws Exception {
        AddressLookup addressLookup = new AddressLookup();
        addressLookup.setCity("Provo");
        addressLookup.setStreet("555 N 358 S #2");
        addressLookup.setState("Utah");
        addressLookup.setZipCode("84664");
        RequestCapturingSender requestCapturingSender = new RequestCapturingSender();
        Client client = new ClientBuilder().withSender(requestCapturingSender).build();

        client.send(addressLookup);
        String expected = "https://api.smartystreets.com/street-address?street=555+N+358+S+%232&city=Provo&state=Utah&zipcode=84664";

        String url = requestCapturingSender.getRequest().getUrl();
        assertEquals(expected, url);
    }

//    @Test
//    public void testCorrectlyAssignsResultsToAddressLookups() throws Exception {
//        Candidate[] results = new GoogleSerializer().deserialize(this.expectedJsonResponse.getBytes(), Candidate[].class);
//        Client client = new ClientBuilder().build();
//
//        client.assignCandidatesToLookups(this.batch, results);
//
//        this.assertFieldsAreCorrect();
//    }

    private void assertFieldsAreCorrect() throws Exception {
        Candidate result = this.lookup1.getResult(0);

        assertNotNull(result);
        assertEquals("CA", result.getComponents().getState());
        assertEquals(37.42357, result.getMetadata().getLatitude(), .00001);
        assertEquals("N", result.getAnalysis().getCmra());

        result = this.lookup1.getResult(1);

        assertNotNull(result);
        assertEquals("1354", result.getComponents().getPlus4Code());
        assertEquals(37.22222, result.getMetadata().getLatitude(), .00001);
        assertEquals("N", result.getAnalysis().getCmra());

        result = this.lookup2.getResult(0);

        assertEquals("1 Infinite Loop", result.getDeliveryLine1());
        assertEquals("95014", result.getComponents().getZipCode());
        assertEquals("Santa Clara", result.getMetadata().getCountyName());
        assertEquals("AABB", result.getAnalysis().getDpvFootnotes());
        assertNotNull(result);

        ArrayList<Candidate> emptyResult = this.lookup3.getResult();

        assertEquals(0, emptyResult.size());
    }

}