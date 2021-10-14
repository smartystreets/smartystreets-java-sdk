package com.smartystreets.api.international_autocomplete;

import com.smartystreets.api.GoogleSerializer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CandidateTest {


    @Test
    public void testFullJSONDeserialization() throws Exception {

        String rawJSON = "{\n" +
                "\"candidates\": [\n" +
                "{\n" +
                "\"street\": \"12TH AV\",\n" +
                "\"locality\": \"OCEAN GROVE\",\n" +
                "\"administrative_area\": \"VIC\",\n" +
                "\"postal_code\": \"3226\",\n" +
                "\"country_iso3\": \"AUS\"\n" +
                "},\n" +
                "{\n" +
                "\"street\": \"TWELFTH AV\",\n" +
                "\"locality\": \"EDEN PARK\",\n" +
                "\"administrative_area\": \"VIC\",\n" +
                "\"postal_code\": \"3757\",\n" +
                "\"country_iso3\": \"AUS\"\n" +
                "}\n" +
                "]\n" +
                "}";

        byte[] bytes = rawJSON.getBytes();

        Result result = new GoogleSerializer().deserialize(bytes, Result.class);
        Candidate[] candidates = result.getCandidates();

        assertEquals("12TH AV", candidates[0].getStreet());
        assertEquals("OCEAN GROVE", candidates[0].getLocality());
        assertEquals("VIC", candidates[0].getAdministrativeArea());
        assertEquals("3226", candidates[0].getPostalCode());
        assertEquals("AUS", candidates[0].getCountryISO3());
        assertEquals("TWELFTH AV", candidates[1].getStreet());
        assertEquals("EDEN PARK", candidates[1].getLocality());
        assertEquals("VIC", candidates[1].getAdministrativeArea());
        assertEquals("3757", candidates[1].getPostalCode());
        assertEquals("AUS", candidates[1].getCountryISO3());
    }
}