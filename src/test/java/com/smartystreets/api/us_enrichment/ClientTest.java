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
import com.smartystreets.api.us_enrichment.result_types.risk.RiskResponse;
import com.smartystreets.api.us_enrichment.lookup_types.risk.RiskLookup;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryLookup;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryCountLookup;
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
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup("123", "group_structural,sale_date", "taco", "");
        client.sendPropertyPrincipal(lookup);

        Assert.assertEquals("http://localhost:8080/123/property/principal?include=group_structural%2Csale_date&exclude=taco", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingFinancialLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new FinancialResponse[]{new FinancialResponse()});
        Client client = new Client(sender, serializer);
        PropertyFinancialLookup lookup = new PropertyFinancialLookup();
        lookup.setSmartyKey("123");
        lookup.setInclude("group_structural,sale_date");
        client.sendPropertyFinancial(lookup);
        Assert.assertEquals("http://localhost:8080/123/property/financial?include=group_structural%2Csale_date", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingGeoReferenceLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new GeoReferenceResponse[]{new GeoReferenceResponse()});
        Client client = new Client(sender, serializer);
        GeoReferenceLookup lookup = new GeoReferenceLookup("");
        lookup.setSmartyKey("123");
        client.sendGeoReference(lookup);
        Assert.assertEquals("http://localhost:8080/123/geo-reference?", capturingSender.getRequest().getUrl());

        lookup = new GeoReferenceLookup("2010");
        lookup.setSmartyKey("123");
        client.sendGeoReference(lookup);
        Assert.assertEquals("http://localhost:8080/123/geo-reference/2010?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingRiskLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new RiskResponse[]{new RiskResponse()});
        Client client = new Client(sender, serializer);
        RiskLookup lookup = new RiskLookup();
        lookup.setSmartyKey("123");

        client.sendRisk(lookup);
        Assert.assertEquals("http://localhost:8080/123/risk?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingSecondaryLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new SecondaryResponse[]{new SecondaryResponse()});
        Client client = new Client(sender, serializer);
        SecondaryLookup lookup = new SecondaryLookup();
        lookup.setSmartyKey("123");

        client.sendSecondary(lookup);
        Assert.assertEquals("http://localhost:8080/123/secondary?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingSecondaryCountLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new SecondaryCountResponse[]{new SecondaryCountResponse()});
        Client client = new Client(sender, serializer);
        SecondaryCountLookup lookup = new SecondaryCountLookup();
        lookup.setSmartyKey("123");

        client.sendSecondaryCount(lookup);
        Assert.assertEquals("http://localhost:8080/123/secondary/count?", capturingSender.getRequest().getUrl());
    }
}
