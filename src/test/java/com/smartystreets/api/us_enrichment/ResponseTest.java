package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.SmartySerializer;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceAttributes;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialAttributes;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialHistoryEntry;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalAttributes;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskAttributes;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.*;
import com.smartystreets.api.us_enrichment.result_types.MatchedAddress;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResponseTest {

    private final SmartySerializer smartySerializer = new SmartySerializer();
    private static final String validFinancialResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"property\",\"data_subset_name\":\"financial\",\"etag\":\"ABCD\",\"attributes\":{\"assessed_improvement_percent\":\"Assessed_Improvement_Percent\",\"veteran_tax_exemption\":\"Veteran_Tax_Exemption\",\"widow_tax_exemption\":\"Widow_Tax_Exemption\",\"financial_history\":[{\"code_title_company\":\"Code_Title_Company\"}]}}]";
    private static final String validPrincipalResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"property\",\"data_subset_name\":\"principal\",\"etag\":\"ABCD\",\"attributes\":{\"1st_floor_sqft\":\"1st_Floor_Sqft\",\"lender_name_2\":\"Lender_Name_2\",\"lender_seller_carry_back\":\"Lender_Seller_Carry_Back\",\"year_built\":\"Year_Built\",\"zoning\":\"Zoning\"}}]";
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
    }

    @Test
    public void testFinancialFieldValues() throws IOException {
        FinancialResponse[] results = smartySerializer.deserialize(validFinancialResponse.getBytes(), FinancialResponse[].class);

        FinancialResponse result = results[0];
        assertEquals("123", result.getSmartyKey());
        assertEquals("property", result.getDatasetName());
        assertEquals("financial", result.getDataSubsetName());
        assertEquals("ABCD", result.getEtag());

        assertNotNull(result.getAttributes());
        FinancialAttributes attributes = result.getAttributes();

        assertEquals("Assessed_Improvement_Percent", attributes.assessed_improvement_percent);
        assertEquals("Veteran_Tax_Exemption", attributes.veteran_tax_exemption);
        assertEquals("Widow_Tax_Exemption", attributes.widow_tax_exemption);

        FinancialHistoryEntry[] financial_history = attributes.financial_history;
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
