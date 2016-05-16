package com.smartystreets.api;

import com.smartystreets.api.us_zipcode.Client;
import com.smartystreets.api.us_zipcode.Lookup;
import com.smartystreets.api.us_zipcode.Result;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GoogleSerializerTest {
    private GoogleSerializer googleSerializer = new GoogleSerializer();
    private String expectedJsonInput;
    private String expectedJsonOutput;

    @Before
    public void setUp() {
        this.expectedJsonInput = "[{\"city\":\"Washington\",\"state\":\"District of Columbia\",\"zipcode\":\"20500\"},{\"city\":\"Provo\",\"input_id\":\"test id\",\"state\":\"Utah\"},{\"zipcode\":\"54321\"}]";
        this.expectedJsonOutput = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"default_city\":\"Washington\",\"county_fips\":\"11001\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Provo\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"default_city\":true,\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";


    }

    @Test
    public void testSerialize() throws Exception {
        ArrayList<Lookup> lookups = new ArrayList<>();
        byte[] lookupBytes;
        lookups.add(new Lookup("Washington", "District of Columbia", "20500"));
        lookups.add(new Lookup("Provo", "Utah").setInputId("test id"));
        lookups.add(new Lookup("54321"));

        lookupBytes = googleSerializer.serialize(lookups);
        assertArrayEquals(this.expectedJsonInput.getBytes(), lookupBytes);
    }

    @Test
    public void testDeserialize() throws Exception {
        Result[] results = googleSerializer.deserialize(expectedJsonOutput.getBytes(), Result[].class);

        /**Analysis*/
        assertNotNull(results[0]);
        assertEquals(0, results[0].getInputIndex());
        assertNotNull(results[0].getCityState(0));
        assertEquals("Washington", results[0].getCityState(0).getCity());
        assertEquals("20500", results[0].getZipCode(0).getZipcode());

        assertNotNull(results[1]);
        assertNull(results[1].getStatus());
        assertEquals("test id", results[1].getInputId());
        assertEquals("Utah", results[1].getCityState(0).getState());
        assertEquals(38.89769, results[1].getZipCode(0).getLatitude(), .00001);

        assertNotNull(results[2]);
        assertNull(results[2].getCityStates());
        assertEquals("invalid_zipcode", results[2].getStatus());
        assertEquals("Invalid ZIP Code.", results[2].getReason());
    }

}