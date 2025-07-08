package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.RequestCapturingSender;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_financial.PropertyFinancialLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
    @Test
    public void testSendingPrincipalLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        PrincipalResponse principalResponse = new PrincipalResponse();
        FakeSerializer serializer = new FakeSerializer(new PrincipalResponse[]{new PrincipalResponse()});
        Client client = new Client(sender, serializer);
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup("123", "group_structural,sale_date", "", "");
        client.sendPropertyPrincipal(lookup);

        Assert.assertEquals("http://localhost:8080/123/property/principal?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingFinancialLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new FinancialResponse[]{new FinancialResponse()});
        Client client = new Client(sender, serializer);
        PropertyFinancialLookup lookup = new PropertyFinancialLookup("123", "group_structural,sale_date", "", "");
        client.sendPropertyFinancial(lookup);

        Assert.assertEquals("http://localhost:8080/123/property/financial?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingGeoReferenceLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new GeoReferenceResponse[]{new GeoReferenceResponse()});
        Client client = new Client(sender, serializer);
        GeoReferenceLookup lookup = new GeoReferenceLookup("123", "");
        client.sendGeoReference(lookup);

        Assert.assertEquals("http://localhost:8080/123/geo-reference?", capturingSender.getRequest().getUrl());
    }
}
