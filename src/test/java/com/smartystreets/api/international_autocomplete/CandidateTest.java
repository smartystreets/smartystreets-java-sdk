package com.smartystreets.api.international_autocomplete;

import com.smartystreets.api.SmartySerializer;
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
            "\"street\": \"MONG FAT STREET\",\n" +
            "\"locality\": \"TUEN MUN\",\n" +
            "\"administrative_area\": \"TUEN MUN DISTRICT\",\n" +
            "\"country_iso3\": \"HKG\"\n" +
            "},\n" +
            "{\n" +
            "\"entries\": 54,\n" +
            "\"address_text\": \"11 Laguna Pky Brechin, ON, L0K 1B0\",\n" +
            "\"address_id\": \"HB5SHA8DBQ8QKS0mED0nRykgGEctOhM2LRs\"\n" +
            "}\n" +
            "]\n" +
            "}";

        byte[] bytes = rawJSON.getBytes();

        Result result = new SmartySerializer().deserialize(bytes, Result.class);
        Candidate[] candidates = result.getCandidates();

        assertEquals("12TH AV", candidates[0].getStreet());
        assertEquals("OCEAN GROVE", candidates[0].getLocality());
        assertEquals("VIC", candidates[0].getAdministrativeArea());
        assertEquals("3226", candidates[0].getPostalCode());
        assertEquals("AUS", candidates[0].getCountryISO3());
        assertEquals("MONG FAT STREET", candidates[1].getStreet());
        assertEquals("TUEN MUN", candidates[1].getLocality());
        assertEquals("TUEN MUN DISTRICT", candidates[1].getAdministrativeArea());
        assertEquals("HKG", candidates[1].getCountryISO3());
        assertEquals(54, candidates[2].getEntries());
        assertEquals("11 Laguna Pky Brechin, ON, L0K 1B0", candidates[2].getAddressText());
        assertEquals("HB5SHA8DBQ8QKS0mED0nRykgGEctOhM2LRs", candidates[2].getAddressID());
    }
}