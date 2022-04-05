package com.smartystreets.api;

import com.smartystreets.api.us_zipcode.Lookup;
import com.smartystreets.api.us_zipcode.Result;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Base64;

import static org.junit.Assert.*;

public class SmartySerializerTest {
    private static final String expectedJsonInput = "[{\"city\":\"Washington\",\"state\":\"District of Columbia\",\"zipcode\":\"20500\"},{\"city\":\"Provo\",\"state\":\"Utah\"},{\"zipcode\":\"54321\"}]";
    private static final String expectedJsonOutput = "[{\"input_index\":0,\"city_states\":[{\"city\":\"Washington\",\"state_abbreviation\":\"DC\",\"state\":\"District of Columbia\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"20500\",\"zipcode_type\":\"S\",\"default_city\":\"Washington\",\"county_fips\":\"11001\",\"county_name\":\"District of Columbia\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":1,\"input_id\":\"test id\",\"city_states\":[{\"city\":\"Provo\",\"state_abbreviation\":\"UT\",\"state\":\"Utah\",\"mailable_city\":true}],\"zipcodes\":[{\"zipcode\":\"84606\",\"zipcode_type\":\"S\",\"county_fips\":\"11501\",\"county_name\":\"Utah\",\"latitude\":38.89769,\"longitude\":-77.03869}]},{\"input_index\":2,\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}]";
    private final SmartySerializer smartySerializer = new SmartySerializer();

    @Test
    public void testSerialize() throws Exception {
        ArrayList<Lookup> lookups = new ArrayList<>();
        byte[] lookupBytes;
        lookups.add(new Lookup("Washington", "District of Columbia", "20500"));
        lookups.add(new Lookup("Provo", "Utah"));
        lookups.add(new Lookup("54321"));

        lookupBytes = smartySerializer.serialize(lookups);
        assertEquals(expectedJsonInput, new String(lookupBytes));
    }

    @Test
    public void testDeserialize() throws Exception {
        Result[] results = smartySerializer.deserialize(expectedJsonOutput.getBytes(), Result[].class);

        assertNotNull(results[0]);
        assertEquals(0, results[0].getInputIndex());
        assertNotNull(results[0].getCity(0));
        assertEquals("Washington", results[0].getCity(0).getCity());
        assertEquals("20500", results[0].getZipCode(0).getZipCode());

        assertNotNull(results[1]);
        assertNull(results[1].getStatus());
        assertEquals("Utah", results[1].getCity(0).getState());
        assertEquals(38.89769, results[1].getZipCode(0).getLatitude(), .00001);

        assertNotNull(results[2]);
        assertNull(results[2].getCities());
        assertEquals("invalid_zipcode", results[2].getStatus());
        assertEquals("Invalid ZIP Code.", results[2].getReason());
    }
}