package com.smartystreets.api.us_street;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.GoogleSerializer;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CandidateTest {


    @Test
    public void testFullJSONDeserialization() throws Exception {

        String rawJSON = "[\n"
                + "{\n"
                + "\"input_id\": \"blah\",\n"
                + "\"input_index\": 0,\n"
                + "\"candidate_index\": 4242,\n"
                + "\"addressee\": \"John Smith\",\n"
                + "\"delivery_line_1\": \"3214 N University Ave # 409\",\n"
                + "\"delivery_line_2\": \"blah blah\",\n"
                + "\"last_line\": \"Provo UT 84604-4405\",\n"
                + "\"delivery_point_barcode\": \"846044405140\",\n"
                + "\"components\": {\n"
                + "\"primary_number\": \"3214\",\n"
                + "\"street_predirection\": \"N\",\n"
                + "\"street_postdirection\": \"Q\",\n"
                + "\"street_name\": \"University\",\n"
                + "\"street_suffix\": \"Ave\",\n"
                + "\"secondary_number\": \"409\",\n"
                + "\"secondary_designator\": \"#\",\n"
                + "\"extra_secondary_number\": \"410\",\n"
                + "\"extra_secondary_designator\": \"Apt\",\n"
                + "\"pmb_number\": \"411\",\n"
                + "\"pmb_designator\": \"Box\",\n"
                + "\"city_name\": \"Provo\",\n"
                + "\"default_city_name\": \"Provo\",\n"
                + "\"state_abbreviation\": \"UT\",\n"
                + "\"zipcode\": \"84604\",\n"
                + "\"plus4_code\": \"4405\",\n"
                + "\"delivery_point\": \"14\",\n"
                + "\"delivery_point_check_digit\": \"0\",\n"
                + "\"urbanization\": \"urbanization\"\n"
                + "},\n"
                + "\"metadata\": {\n"
                + "\"record_type\": \"S\",\n"
                + "\"zip_type\": \"Standard\",\n"
                + "\"county_fips\": \"49049\",\n"
                + "\"county_name\": \"Utah\",\n"
                + "\"carrier_route\": \"C016\",\n"
                + "\"congressional_district\": \"03\",\n"
                + "\"building_default_indicator\": \"hi\",\n"
                + "\"rdi\": \"Commercial\",\n"
                + "\"elot_sequence\": \"0016\",\n"
                + "\"elot_sort\": \"A\",\n"
                + "\"latitude\": 40.27658,\n"
                + "\"longitude\": -111.65759,\n"
                + "\"precision\": \"Zip9\",\n"
                + "\"time_zone\": \"Mountain\",\n"
                + "\"utc_offset\": -7,\n"
                + "\"dst\": true,\n"
                + "\"ews_match\": true\n"
                + "},\n"
                + "\"analysis\": {\n"
                + "\"dpv_match_code\": \"S\",\n"
                + "\"dpv_footnotes\": \"AACCRR\",\n"
                + "\"dpv_cmra\": \"Y\",\n"
                + "\"dpv_vacant\": \"N\",\n"
                + "\"dpv_no_stat\": \"N\",\n"
                + "\"active\": \"Y\",\n"
                + "\"footnotes\": \"footnotes\",\n"
                + "\"lacslink_code\": \"lacslink_code\",\n"
                + "\"lacslink_indicator\": \"lacslink_indicator\",\n"
                + "\"suitelink_match\": true,\n"
                + "\"enhanced_match\": \"enhanced_match\"\n"
                + "}\n"
                + "}\n"
                + "]\n";
        byte[] bytes = rawJSON.getBytes();

        Candidate[] candidates = new GoogleSerializer().deserialize(bytes, Candidate[].class);

        assertEquals(0, candidates[0].getInputIndex());
        assertEquals(4242, candidates[0].getCandidateIndex());
        assertEquals("blah", candidates[0].getInputId());
        assertEquals("John Smith", candidates[0].getAddressee());
        assertEquals("3214 N University Ave # 409", candidates[0].getDeliveryLine1());
        assertEquals("blah blah", candidates[0].getDeliveryLine2());
        assertEquals("Provo UT 84604-4405", candidates[0].getLastLine());
        assertEquals("846044405140", candidates[0].getDeliveryPointBarcode());
        assertEquals("3214", candidates[0].getComponents().getPrimaryNumber());
        assertEquals("N", candidates[0].getComponents().getStreetPredirection());
        assertEquals("Q", candidates[0].getComponents().getStreetPostdirection());
        assertEquals("Ave", candidates[0].getComponents().getStreetSuffix());
        assertEquals("409", candidates[0].getComponents().getSecondaryNumber());
        assertEquals("#", candidates[0].getComponents().getSecondaryDesignator());
        assertEquals("410", candidates[0].getComponents().getExtraSecondaryNumber());
        assertEquals("Apt", candidates[0].getComponents().getExtraSecondaryDesignator());
        assertEquals("411", candidates[0].getComponents().getPmbNumber());
        assertEquals("Box", candidates[0].getComponents().getPmbDesignator());
        assertEquals("Provo", candidates[0].getComponents().getCityName());
        assertEquals("Provo", candidates[0].getComponents().getDefaultCityName());
        assertEquals("UT", candidates[0].getComponents().getState());
        assertEquals("84604", candidates[0].getComponents().getZipCode());
        assertEquals("4405", candidates[0].getComponents().getPlus4Code());
        assertEquals("14", candidates[0].getComponents().getDeliveryPoint());
        assertEquals("0", candidates[0].getComponents().getDeliveryPointCheckDigit());
        assertEquals("urbanization", candidates[0].getComponents().getUrbanization());
        assertEquals("S", candidates[0].getMetadata().getRecordType());
        assertEquals("Standard", candidates[0].getMetadata().getZipType());
        assertEquals("49049", candidates[0].getMetadata().getCountyFips());
        assertEquals("Utah", candidates[0].getMetadata().getCountyName());
        assertEquals("C016", candidates[0].getMetadata().getCarrierRoute());
        assertEquals("03", candidates[0].getMetadata().getCongressionalDistrict());
        assertEquals("hi", candidates[0].getMetadata().getBuildingDefaultIndicator());
        assertEquals("Commercial", candidates[0].getMetadata().getRdi());
        assertEquals("0016", candidates[0].getMetadata().getElotSequence());
        assertEquals("A", candidates[0].getMetadata().getElotSort());
        assertEquals(40.27658, candidates[0].getMetadata().getLatitude(), 1);
        assertEquals(-111.65759, candidates[0].getMetadata().getLongitude(), 1);
        assertEquals("Zip9", candidates[0].getMetadata().getPrecision());
        assertEquals("Mountain", candidates[0].getMetadata().getTimeZone());
        assertEquals(-7, candidates[0].getMetadata().getUtcOffset(), 1);
        assertEquals(true, candidates[0].getMetadata().obeysDst());
        assertEquals(true, candidates[0].getMetadata().isEwsMatch());
        assertEquals("S", candidates[0].getAnalysis().getDpvMatchCode());
        assertEquals("AACCRR", candidates[0].getAnalysis().getDpvFootnotes());
        assertEquals("Y", candidates[0].getAnalysis().getCmra());
        assertEquals("N", candidates[0].getAnalysis().getVacant());
        assertEquals("N", candidates[0].getAnalysis().getNo_stat());
        assertEquals("Y", candidates[0].getAnalysis().getActive());
        assertEquals("footnotes", candidates[0].getAnalysis().getFootnotes());
        assertEquals("lacslink_code", candidates[0].getAnalysis().getLacsLinkCode());
        assertEquals("lacslink_indicator", candidates[0].getAnalysis().getLacsLinkIndicator());
        assertEquals(true, candidates[0].getAnalysis().isSuiteLinkMatch());
        assertEquals("enhanced_match", candidates[0].getAnalysis().getEnhancedMatch());
    }
}