package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.SmartySerializer;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceAttributes;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalAttributes;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalFinancialHistoryEntry;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskAttributes;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskResponse;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessAttributes;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessDetailResponse;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessEntry;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessSummaryResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.*;
import com.smartystreets.api.us_enrichment.result_types.MatchedAddress;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResponseTest {

    private final SmartySerializer smartySerializer = new SmartySerializer();
    private static final String validPrincipalResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"property\",\"data_subset_name\":\"principal\",\"etag\":\"ABCD\",\"attributes\":{\"1st_floor_sqft\":\"1st_Floor_Sqft\",\"lender_name_2\":\"Lender_Name_2\",\"lender_seller_carry_back\":\"Lender_Seller_Carry_Back\",\"year_built\":\"Year_Built\",\"zoning\":\"Zoning\",\"financial_history\":[{\"code_title_company\":\"Code_Title_Company\"}]}}]";
    private static final String validSecondaryResponse = "[{\"smarty_key\":\"123\",\"root_address\":{\"secondary_count\":1,\"smarty_key\":\"root_smartykey\",\"primary_number\":\"root_primary\",\"street_predirection\":\"root_predirection\",\"street_name\":\"root_streetname\",\"street_suffix\":\"root_street_suffix\",\"street_postdirection\":\"root_postdirection\",\"city_name\":\"root_city\",\"state_abbreviation\":\"root_state\",\"zipcode\":\"root_zipcode\",\"plus4_code\":\"root_plus4\"},\"aliases\":[{\"smarty_key\":\"alias_smartykey\",\"primary_number\":\"alias_primary\",\"street_predirection\":\"alias_predirectional\",\"street_name\":\"alias_streetname\",\"street_suffix\":\"alias_streetsuffix\",\"street_postdirection\":\"alias_postdirection\",\"city_name\":\"alias_city\",\"state_abbreviation\":\"alias_state\",\"zipcode\":\"alias_zipcode\",\"plus4_code\":\"alias_plus4\"}],\"secondaries\":[{\"smarty_key\":\"secondary_smartykey\",\"secondary_designator\":\"secondary_designator\",\"secondary_number\":\"secondary_number\",\"plus4_code\":\"secondary_plus4\"}]}]";
    private static final String validSecondaryCountResponse = "[{\"smarty_key\":\"123\",\"count\":10}]";
    private static final String validGeoReferenceResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"geo-reference\",\"etag\":\"ABCD\",\"attributes\":{\"census_block\":{\"accuracy\":\"block\",\"geoid\":\"180759630002012\"},\"census_county_division\":{\"accuracy\":\"exact\",\"code\":\"1807581764\",\"name\":\"Wayne\"},\"place\":{\"accuracy\":\"exact\",\"code\":\"1861236\",\"name\":\"Portland\",\"type\":\"incorporated\"}}}]";
    private static final String validRiskResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"risk\",\"etag\":\"ABCD\",\"matched_address\":{\"street\":\"street value\"},\"attributes\":{\"AGRIVALUE\":\"agrivalue\",\"AREA\":\"area\",\"NRI_VER\":\"nri_ver\"}}]";

    @Test
    public void testPrincipalFieldValues() throws IOException {
        PrincipalResponse[] results = smartySerializer.deserialize(validPrincipalResponse.getBytes(), PrincipalResponse[].class);

        PrincipalResponse result = results[0];
        assertEquals("123", result.getSmartyKey());
        assertEquals("property", result.getDatasetName());
        assertEquals("principal", result.getDataSubsetName());
        assertEquals("ABCD", result.getEtag());

        assertNotNull(result.getAttributes());
        PrincipalAttributes attributes = result.getAttributes();

        assertEquals("1st_Floor_Sqft", attributes.first_floor_sqft);
        assertEquals("Lender_Name_2", attributes.lender_name_2);
        assertEquals("Zoning", attributes.zoning);

        PrincipalFinancialHistoryEntry[] financial_history = attributes.financial_history;
        assertEquals(1, financial_history.length);
        assertEquals("Code_Title_Company", financial_history[0].code_title_company);
    }

    @Test
    public void testSecondaryFieldValues() throws IOException {
        SecondaryResponse[] results = smartySerializer.deserialize(validSecondaryResponse.getBytes(), SecondaryResponse[].class);

        SecondaryResponse result = results[0];
        assertEquals("123", result.getSmartyKey());

        assertNotNull(result.getRootAddress());
        RootAddress rootAddress = result.getRootAddress();
        assertEquals(1, rootAddress.getSecondaryCount());
        assertEquals("root_smartykey", rootAddress.getSmartyKey());
        assertEquals("root_primary", rootAddress.getPrimaryNumber());
        assertEquals("root_predirection", rootAddress.getStreetPredirection());
        assertEquals("root_streetname", rootAddress.getStreetName());
        assertEquals("root_street_suffix", rootAddress.getStreetSuffix());
        assertEquals("root_postdirection", rootAddress.getStreetPostdirection());
        assertEquals("root_city", rootAddress.getCityName());
        assertEquals("root_state", rootAddress.getStateAbbreviation());
        assertEquals("root_zipcode", rootAddress.getZipcode());
        assertEquals("root_plus4", rootAddress.getPlus4Code());


        assertNotNull(result.getAliases());
        ArrayList<Alias> aliases = result.getAliases();
        assertEquals(1, aliases.size());
        Alias alias = aliases.get(0);
        assertEquals("alias_smartykey", alias.getSmartyKey());
        assertEquals("alias_city", alias.getCityName());
        assertEquals("alias_plus4", alias.getPlus4Code());
        assertEquals("alias_primary", alias.getPrimaryNumber());
        assertEquals("alias_state", alias.getStateAbbreviation());
        assertEquals("alias_streetname", alias.getStreetName());
        assertEquals("alias_predirectional", alias.getStreetPredirection());
        assertEquals("alias_zipcode", alias.getZipcode());
        assertEquals("alias_postdirection", alias.getStreetPostdirection());
        assertEquals("alias_streetsuffix", alias.getStreetSuffix());

        assertNotNull(result.getSecondaries());
        ArrayList<Secondary> secondaries = result.getSecondaries();
        assertEquals(1, secondaries.size());
        Secondary secondary = secondaries.get(0);
        assertEquals("secondary_smartykey", secondary.getSmartyKey());
        assertEquals("secondary_designator", secondary.getSecondaryDesignator());
        assertEquals("secondary_number", secondary.getSecondaryNumber());
        assertEquals("secondary_plus4", secondary.getPlus4Code());
    }

    @Test
    public void testSecondaryCountFieldValues() throws IOException {
        SecondaryCountResponse[] results = smartySerializer.deserialize(validSecondaryCountResponse.getBytes(), SecondaryCountResponse[].class);
        SecondaryCountResponse result = results[0];

        assertEquals("123", result.getSmartyKey());
        assertEquals(10, result.getCount());
    }

    @Test
    public void testGeoReferenceFieldValues() throws IOException {
        GeoReferenceResponse[] results = smartySerializer.deserialize(validGeoReferenceResponse.getBytes(), GeoReferenceResponse[].class);

        GeoReferenceResponse result = results[0];
        assertEquals("123", result.getSmartyKey());
        assertEquals("ABCD", result.getEtag());

        assertNotNull(result.getAttributes());
        GeoReferenceAttributes attributes = result.getAttributes();

        // Assert for census_block
        assertNotNull(attributes.census_block);
        assertEquals("block", attributes.census_block.accuracy);
        assertEquals("180759630002012", attributes.census_block.geoid);

        // Assert for census_county_division
        assertNotNull(attributes.census_county_division);
        assertEquals("exact", attributes.census_county_division.accuracy);
        assertEquals("1807581764", attributes.census_county_division.code);
        assertEquals("Wayne", attributes.census_county_division.name);

        // Assert for place
        assertNotNull(attributes.place);
        assertEquals("exact", attributes.place.accuracy);
        assertEquals("1861236", attributes.place.code);
        assertEquals("Portland", attributes.place.name);
        assertEquals("incorporated", attributes.place.type);
    }

    private static final String validBusinessSummaryResponse = "[{\"smarty_key\":\"1962995076\",\"data_set_name\":\"business\",\"businesses\":[{\"company_name\":\"Acme Corp\",\"business_id\":\"ABC123\"},{\"company_name\":\"Beta LLC\",\"business_id\":\"DEF456\"}]}]";
    private static final String validBusinessDetailResponse = "[{\"smarty_key\":\"7\",\"data_set_name\":\"business\",\"business_id\":\"ABC123\",\"attributes\":{\"company_name\":\"Acme Corp\",\"ein\":\"12-3456789\",\"primary_sic_code\":\"1234\",\"fortune_1000_rank\":\"250\",\"year_established\":\"1998\",\"url\":\"acme.example\",\"email\":\"hello@acme.example\",\"phone\":\"555-1234\",\"latitude\":\"40.7128\",\"longitude\":\"-74.0060\",\"city_name\":\"New York\",\"state_abbreviation\":\"NY\",\"zipcode\":\"10001\"}}]";

    @Test
    public void testBusinessSummaryFieldValues() throws IOException {
        BusinessSummaryResponse[] results = smartySerializer.deserialize(validBusinessSummaryResponse.getBytes(), BusinessSummaryResponse[].class);

        BusinessSummaryResponse result = results[0];
        assertEquals("1962995076", result.getSmartyKey());
        assertEquals("business", result.getDataSetName());

        BusinessEntry[] entries = result.getBusinesses();
        assertNotNull(entries);
        assertEquals(2, entries.length);
        assertEquals("Acme Corp", entries[0].getCompanyName());
        assertEquals("ABC123", entries[0].getBusinessId());
        assertEquals("Beta LLC", entries[1].getCompanyName());
        assertEquals("DEF456", entries[1].getBusinessId());
    }

    @Test
    public void testBusinessDetailFieldValues() throws IOException {
        BusinessDetailResponse[] results = smartySerializer.deserialize(validBusinessDetailResponse.getBytes(), BusinessDetailResponse[].class);

        BusinessDetailResponse result = results[0];
        assertEquals("7", result.getSmartyKey());
        assertEquals("business", result.getDataSetName());
        assertEquals("ABC123", result.getBusinessId());

        BusinessAttributes attributes = result.getAttributes();
        assertNotNull(attributes);
        assertEquals("Acme Corp", attributes.companyName);
        assertEquals("12-3456789", attributes.ein);
        assertEquals("1234", attributes.primarySicCode);
        assertEquals("250", attributes.fortune1000Rank);
        assertEquals("1998", attributes.yearEstablished);
        assertEquals("acme.example", attributes.url);
        assertEquals("hello@acme.example", attributes.email);
        assertEquals("555-1234", attributes.phone);
        assertEquals("40.7128", attributes.latitude);
        assertEquals("-74.0060", attributes.longitude);
        assertEquals("New York", attributes.cityName);
        assertEquals("NY", attributes.stateAbbreviation);
        assertEquals("10001", attributes.zipcode);
    }

    @Test
    public void testRiskFieldValues() throws IOException {
        RiskResponse[] results = smartySerializer.deserialize(validRiskResponse.getBytes(), RiskResponse[].class);

        RiskResponse result = results[0];
        assertEquals("123", result.getSmartyKey());
        assertEquals("ABCD", result.getEtag());

        MatchedAddress matchedAddress = result.getMatchedAddress();
        assertEquals("street value", matchedAddress.street);

        assertNotNull(result.getAttributes());
        RiskAttributes attributes = result.getAttributes();
        assertEquals("agrivalue", attributes.AGRIVALUE);
        assertEquals("area", attributes.AREA);
        assertEquals("nri_ver", attributes.NRI_VER);
    }

}
