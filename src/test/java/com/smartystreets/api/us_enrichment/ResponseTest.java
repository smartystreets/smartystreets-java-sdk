package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.SmartySerializer;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceAttributes;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialAttributes;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialHistoryEntry;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalAttributes;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ResponseTest {

    private final SmartySerializer smartySerializer = new SmartySerializer();
    private static final String validFinancialResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"property\",\"data_subset_name\":\"financial\",\"etag\":\"5432\",\"attributes\":{\"assessed_improvement_percent\":\"Assessed_Improvement_Percent\",\"veteran_tax_exemption\":\"Veteran_Tax_Exemption\",\"widow_tax_exemption\":\"Widow_Tax_Exemption\",\"financial_history\":[{\"code_title_company\":\"Code_Title_Company\"}]}}]";
    private static final String validPrincipalResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"property\",\"data_subset_name\":\"principal\",\"etag\":\"5432\",\"attributes\":{\"1st_floor_sqft\":\"1st_Floor_Sqft\",\"lender_name_2\":\"Lender_Name_2\",\"lender_seller_carry_back\":\"Lender_Seller_Carry_Back\",\"year_built\":\"Year_Built\",\"zoning\":\"Zoning\"}}]";
    private static final String validGeoReferenceResponse = "[{\"smarty_key\":\"123\",\"data_set_name\":\"geo-reference\",\"etag\":\"5432\",\"attributes\":{\"census_block\":{\"accuracy\":\"block\",\"geoid\":\"180759630002012\"},\"census_county_division\":{\"accuracy\":\"exact\",\"code\":\"1807581764\",\"name\":\"Wayne\"},\"place\":{\"accuracy\":\"exact\",\"code\":\"1861236\",\"name\":\"Portland\",\"type\":\"incorporated\"}}}]";


    @Test
    public void testPrincipalFieldsGetFillledInCorrectly() throws IOException {
        PrincipalResponse[] results = smartySerializer.deserialize(validPrincipalResponse.getBytes(), PrincipalResponse[].class);

        PrincipalResponse result = results[0];
        assertEquals("123", result.getSmartyKey());
        assertEquals("property", result.getDatasetName());
        assertEquals("principal", result.getDataSubsetName());
        assertEquals("5432", result.getEtag());

        assertNotNull(result.getAttributes());
        PrincipalAttributes attributes = result.getAttributes();

        assertEquals("1st_Floor_Sqft", attributes.first_floor_sqft);
        assertEquals("Lender_Name_2", attributes.lender_name_2);
        assertEquals("Zoning", attributes.zoning);
    }

    @Test
    public void testFinancialFieldsGetFilledInCorrectly() throws IOException {
        FinancialResponse[] results = smartySerializer.deserialize(validFinancialResponse.getBytes(), FinancialResponse[].class);

        FinancialResponse result = results[0];
        assertEquals("123", result.getSmartyKey());
        assertEquals("property", result.getDatasetName());
        assertEquals("financial", result.getDataSubsetName());
        assertEquals("5432", result.getEtag());

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
    public void testGeoReferenceFieldsGetFilledInCorrectly() throws IOException {
        GeoReferenceResponse[] results = smartySerializer.deserialize(validGeoReferenceResponse.getBytes(), GeoReferenceResponse[].class);

        GeoReferenceResponse result = results[0];
        assertEquals("123", result.getSmartyKey());
        assertEquals("5432", result.getEtag());

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

}
