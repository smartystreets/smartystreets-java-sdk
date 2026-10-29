package com.smartystreets.api.international_postal_code;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.smartystreets.api.SmartySerializer;

public class CandidateTest {

    @Test
    public void testAllFieldsFilledCorrectly() throws IOException {
        String responsePayload = "[{\"input_id\":\"1\",\"administrative_area\":\"2\"," +
                "\"sub_administrative_area\":\"3\",\"super_administrative_area\":\"4\"," +
                "\"country_iso_3\":\"5\",\"locality\":\"6\",\"dependent_locality\":\"7\"," +
                "\"dependent_locality_name\":\"8\",\"double_dependent_locality\":\"9\"," +
                "\"thoroughfare\":\"10\",\"postal_code\":\"11\",\"postal_code_extra\":\"12\"}]";

        SmartySerializer smartySerializer = new SmartySerializer();
        Candidate candidate = smartySerializer.deserialize(responsePayload.getBytes(), Candidate[].class)[0];

        assertEquals("1", candidate.getInputId());
        assertEquals("2", candidate.getAdministrativeArea());
        assertEquals("3", candidate.getSubAdministrativeArea());
        assertEquals("4", candidate.getSuperAdministrativeArea());
        assertEquals("5", candidate.getCountryIso3());
        assertEquals("6", candidate.getLocality());
        assertEquals("7", candidate.getDependentLocality());
        assertEquals("8", candidate.getDependentLocalityName());
        assertEquals("9", candidate.getDoubleDependentLocality());
        assertEquals("10", candidate.getThoroughfare());
        assertEquals("11", candidate.getPostalCode());
        assertEquals("12", candidate.getPostalCodeExtra());
    }
}
