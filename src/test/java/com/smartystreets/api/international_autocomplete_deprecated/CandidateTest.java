package com.smartystreets.api.international_autocomplete_deprecated;

import com.smartystreets.api.SmartySerializer;
import com.smartystreets.api.international_autocomplete_deprecated.Candidate;
import com.smartystreets.api.international_autocomplete_deprecated.Result;
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
                "\"sub_administrative_area\": \"WHITTLESEA\"," +
                "\"postal_code\": \"3226\",\n" +
                "\"country_iso3\": \"AUS\"\n" +
                "},\n" +
                "{\n" +
                "\"street\": \"MONG FAT STREET\",\n" +
                "\"locality\": \"TUEN MUN\",\n" +
                "\"administrative_area\": \"TUEN MUN DISTRICT\",\n" +
                "\"super_administrative_area\": \"HONG KONG\",\n" +
                "\"country_iso3\": \"HKG\"\n" +
                "}\n" +
                "]\n" +
                "}";

        byte[] bytes = rawJSON.getBytes();

        Result result = new SmartySerializer().deserialize(bytes, Result.class);
        Candidate[] candidates = result.getCandidates();

        assertEquals("12TH AV", candidates[0].getStreet());
        assertEquals("OCEAN GROVE", candidates[0].getLocality());
        assertEquals("VIC", candidates[0].getAdministrativeArea());
        assertEquals("WHITTLESEA", candidates[0].getSubAdministrativeArea());
        assertEquals("3226", candidates[0].getPostalCode());
        assertEquals("AUS", candidates[0].getCountryISO3());
        assertEquals("MONG FAT STREET", candidates[1].getStreet());
        assertEquals("TUEN MUN", candidates[1].getLocality());
        assertEquals("TUEN MUN DISTRICT", candidates[1].getAdministrativeArea());
        assertEquals("HONG KONG", candidates[1].getSuperAdministrativeArea());
        assertEquals("HKG", candidates[1].getCountryISO3());
    }
}