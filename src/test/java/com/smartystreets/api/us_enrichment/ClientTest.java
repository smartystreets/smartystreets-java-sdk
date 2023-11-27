package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.RequestCapturingSender;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
    @Test
    public void testSendingPrincipalLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new PrincipalResponse[]{new PrincipalResponse()});
        Client client = new Client(sender, serializer);

        client.sendPropertyPrincipalLookup("123");

        Assert.assertEquals("http://localhost:8080/123/property/principal?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingFinancialLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new FinancialResponse[]{new FinancialResponse()});
        Client client = new Client(sender, serializer);

        client.sendPropertyFinancialLookup("123");

        Assert.assertEquals("http://localhost:8080/123/property/financial?", capturingSender.getRequest().getUrl());
    }
}
