package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.SmartySerializer;
import com.smartystreets.api.StatusCodeSender;
import com.smartystreets.api.URLPrefixSender;
import com.smartystreets.api.exceptions.NotModifiedException;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.mocks.FakeSerializer;
import com.smartystreets.api.mocks.MockSender;
import com.smartystreets.api.mocks.RequestCapturingSender;
import com.smartystreets.api.us_enrichment.lookup_types.business.BusinessDetailLookup;
import com.smartystreets.api.us_enrichment.lookup_types.business.BusinessSummaryLookup;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.lookup_types.risk.RiskLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryCountLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryLookup;
import com.smartystreets.api.us_enrichment.result_types.AddressSearch;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessDetailResponse;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessSummaryResponse;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;
import okhttp3.Headers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ClientTest {
    @Test
    public void testSendingPrincipalLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new PrincipalResponse[]{new PrincipalResponse()});
        Client client = new Client(sender, serializer);
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup("123", "group_structural,sale_date", "taco", "");
        lookup.setFeatures("financial");
        client.sendPropertyPrincipal(lookup);

        assertEquals("http://localhost:8080/123/property/principal?include=group_structural%2Csale_date&exclude=taco&features=financial", capturingSender.getRequest().getUrl());
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
        assertEquals("http://localhost:8080/123/geo-reference?", capturingSender.getRequest().getUrl());

        lookup = new GeoReferenceLookup("2010");
        lookup.setSmartyKey("123");
        client.sendGeoReference(lookup);
        assertEquals("http://localhost:8080/123/geo-reference/2010?", capturingSender.getRequest().getUrl());
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
        assertEquals("http://localhost:8080/123/risk?", capturingSender.getRequest().getUrl());
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
        assertEquals("http://localhost:8080/123/secondary?", capturingSender.getRequest().getUrl());
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
        assertEquals("http://localhost:8080/123/secondary/count?", capturingSender.getRequest().getUrl());
    }

    // ================ Business.Summary URL tests ================

    @Test
    public void testSendingFullyPopulatedBusinessLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new BusinessSummaryResponse[]{new BusinessSummaryResponse()});
        Client client = new Client(sender, serializer);

        client.sendBusinessSummary("1");

        assertEquals("http://localhost:8080/1/business?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingBusinessSummaryComponentsLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new BusinessSummaryResponse[]{new BusinessSummaryResponse()});
        Client client = new Client(sender, serializer);

        BusinessSummaryLookup lookup = new BusinessSummaryLookup();
        lookup.setAddressSearch(new AddressSearch()
                .withStreet("street")
                .withCity("city")
                .withState("state")
                .withZipcode("zipcode"));
        client.sendBusinessSummary(lookup);

        assertEquals("http://localhost:8080/search/business?street=street&city=city&state=state&zipcode=zipcode", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testSendingBusinessSummaryFreeformLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new BusinessSummaryResponse[]{new BusinessSummaryResponse()});
        Client client = new Client(sender, serializer);

        BusinessSummaryLookup lookup = new BusinessSummaryLookup();
        lookup.setAddressSearch(new AddressSearch().withFreeform("freeform"));
        client.sendBusinessSummary(lookup);

        assertEquals("http://localhost:8080/search/business?freeform=freeform", capturingSender.getRequest().getUrl());
    }

    // ================ Business.Detail URL tests ================

    @Test
    public void testSendingBusinessDetailLookupUrl() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        client.sendBusinessDetail("ABC123");

        assertEquals("http://localhost:8080/business/ABC123?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testBusinessDetailUrlEncodesReservedChars() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        client.sendBusinessDetail("a/b?c#d");

        assertEquals("http://localhost:8080/business/a%2Fb%3Fc%23d?", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testBusinessDetailSendsRequestEtagHeader() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC123");
        lookup.setRequestEtag("xyz-789");
        client.sendBusinessDetail(lookup);

        Request request = capturingSender.getRequest();
        assertTrue(request.getHeaders().containsKey("Etag"));
        assertEquals("xyz-789", request.getHeaders().get("Etag"));
    }

    @Test
    public void testBusinessDetailIncludeFieldsLandInUrl() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC123");
        lookup.setIncludeFields("phone");
        client.sendBusinessDetail(lookup);

        assertEquals("http://localhost:8080/business/ABC123?include=phone", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testBusinessDetailExcludeFieldsLandInUrl() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC123");
        lookup.setExcludeFields("credit_score");
        client.sendBusinessDetail(lookup);

        assertEquals("http://localhost:8080/business/ABC123?exclude=credit_score", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testBusinessDetailCustomParametersLandInUrl() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC123");
        lookup.addCustomParameter("experimental", "1");
        lookup.addCustomParameter("trace", "on");
        client.sendBusinessDetail(lookup);

        assertEquals("http://localhost:8080/business/ABC123?experimental=1&trace=on", capturingSender.getRequest().getUrl());
    }

    @Test
    public void testBusinessDetailAllParametersCombined() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC123");
        lookup.setIncludeFields("phone");
        lookup.setExcludeFields("credit_score");
        lookup.addCustomParameter("trace", "on");
        client.sendBusinessDetail(lookup);

        assertEquals("http://localhost:8080/business/ABC123?include=phone&exclude=credit_score&trace=on", capturingSender.getRequest().getUrl());
    }

    // ================ Validation tests ================

    @Test
    public void testRejectNullBusinessId() {
        Client client = new Client(new RequestCapturingSender(), new FakeSerializer(new BusinessDetailResponse[0]));
        assertThrows(SmartyException.class, () -> client.sendBusinessDetail((String) null));
    }

    @Test
    public void testRejectEmptyBusinessId() {
        Client client = new Client(new RequestCapturingSender(), new FakeSerializer(new BusinessDetailResponse[0]));
        assertThrows(SmartyException.class, () -> client.sendBusinessDetail(""));
    }

    @Test
    public void testRejectWhitespaceBusinessId() {
        Client client = new Client(new RequestCapturingSender(), new FakeSerializer(new BusinessDetailResponse[0]));
        assertThrows(SmartyException.class, () -> client.sendBusinessDetail("   "));
    }

    @Test
    public void testRejectWhitespaceSmartyKeyOnSummaryLookup() {
        Client client = new Client(new RequestCapturingSender(), new FakeSerializer(new BusinessSummaryResponse[0]));
        assertThrows(SmartyException.class, () -> client.sendBusinessSummary("   "));
    }

    @Test
    public void testRejectWhitespaceOnAllStandardLookupFields() {
        Client client = new Client(new RequestCapturingSender(), new FakeSerializer(new BusinessSummaryResponse[0]));

        BusinessSummaryLookup lookup = new BusinessSummaryLookup("   ");
        lookup.setAddressSearch(new AddressSearch().withStreet("   ").withFreeform("   "));

        assertThrows(SmartyException.class, () -> client.sendBusinessSummary(lookup));
    }

    // ================ Business.Detail deserialization tests ================

    @Test
    public void testBusinessDetailRejectsMultipleResults() {
        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        String json = "[{\"smarty_key\":\"1\"},{\"smarty_key\":\"2\"}]";
        SmartySerializer serializer = new SmartySerializer();
        assertThrows(SmartyException.class, () -> lookup.deserializeAndSetResults(serializer, json.getBytes()));
        assertNull(lookup.getResult());
    }

    @Test
    public void testBusinessDetailAcceptsSingleResult() throws Exception {
        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        String json = "[{\"smarty_key\":\"7\",\"data_set_name\":\"business\",\"business_id\":\"ABC\",\"attributes\":{\"company_name\":\"Acme Corp\"}}]";
        SmartySerializer serializer = new SmartySerializer();
        lookup.deserializeAndSetResults(serializer, json.getBytes());

        BusinessDetailResponse result = lookup.getResult();
        assertNotNull(result);
        assertEquals("ABC", result.getBusinessId());
        assertNotNull(result.getAttributes());
        assertEquals("Acme Corp", result.getAttributes().companyName);
    }

    @Test
    public void testBusinessDetailAcceptsEmptyResults() throws Exception {
        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        SmartySerializer serializer = new SmartySerializer();
        lookup.deserializeAndSetResults(serializer, "[]".getBytes());
        assertNull(lookup.getResult());
    }

    // ================ Request-etag plumbing on existing lookups ================

    @Test
    public void testEnrichmentSummaryLookupSendsEtagHeader() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        FakeSerializer serializer = new FakeSerializer(new PrincipalResponse[]{new PrincipalResponse()});
        Client client = new Client(sender, serializer);

        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup();
        lookup.setSmartyKey("1");
        lookup.setRequestEtag("abc-123");
        client.sendPropertyPrincipal(lookup);

        Request request = capturingSender.getRequest();
        assertTrue(request.getHeaders().containsKey("Etag"));
        assertEquals("abc-123", request.getHeaders().get("Etag"));
    }

    // ================ Response-etag capture on Lookup ================

    @Test
    public void testBusinessDetailCapturesResponseEtagOnLookup() throws Exception {
        Headers headers = new Headers.Builder().add("Etag", "server-etag-1").build();
        MockSender sender = new MockSender(new Response(200, "[]".getBytes(), headers));
        Client client = new Client(sender, new SmartySerializer());

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        client.sendBusinessDetail(lookup);

        assertEquals("server-etag-1", lookup.getResponseEtag());
    }

    @Test
    public void testResponseEtagDoesNotClobberRequestEtag() throws Exception {
        Headers headers = new Headers.Builder().add("Etag", "server-etag-2").build();
        MockSender sender = new MockSender(new Response(200, "[]".getBytes(), headers));
        Client client = new Client(sender, new SmartySerializer());

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        lookup.setRequestEtag("caller-etag");
        client.sendBusinessDetail(lookup);

        assertEquals("caller-etag", lookup.getRequestEtag());
        assertEquals("server-etag-2", lookup.getResponseEtag());
    }

    @Test
    public void testBusinessDetailCaseInsensitiveResponseEtagHeader() throws Exception {
        Headers headers = new Headers.Builder().add("ETag", "standard-cased-etag").build();
        MockSender sender = new MockSender(new Response(200, "[]".getBytes(), headers));
        Client client = new Client(sender, new SmartySerializer());

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        client.sendBusinessDetail(lookup);

        assertEquals("standard-cased-etag", lookup.getResponseEtag());
    }

    @Test
    public void testSummaryCapturesResponseEtagOnLookup() throws Exception {
        Headers headers = new Headers.Builder().add("Etag", "server-etag-summary").build();
        MockSender sender = new MockSender(new Response(200, "[]".getBytes(), headers));
        Client client = new Client(sender, new SmartySerializer());

        BusinessSummaryLookup lookup = new BusinessSummaryLookup("1");
        client.sendBusinessSummary(lookup);

        assertEquals("server-etag-summary", lookup.getResponseEtag());
    }

    @Test
    public void testResponseEtagNullWhenHeaderAbsent() throws Exception {
        MockSender sender = new MockSender(new Response(200, "[]".getBytes()));
        Client client = new Client(sender, new SmartySerializer());

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        lookup.setRequestEtag("caller-etag");
        client.sendBusinessDetail(lookup);

        assertEquals("caller-etag", lookup.getRequestEtag());
        assertNull(lookup.getResponseEtag());
    }

    // ================ Regression: the old result[0].setEtag(header) hack is gone ================

    @Test
    public void testGeoReferenceLookupNoLongerSetsResultEtagFromHeader() throws Exception {
        String json = "[{\"smarty_key\":\"123\",\"data_set_name\":\"geo-reference\"}]";
        Headers headers = new Headers.Builder().add("Etag", "from-header").build();
        MockSender sender = new MockSender(new Response(200, json.getBytes(), headers));
        Client client = new Client(sender, new SmartySerializer());

        GeoReferenceLookup lookup = new GeoReferenceLookup("");
        lookup.setSmartyKey("123");
        client.sendGeoReference(lookup);

        // Response etag goes on the lookup, not on result[0]
        assertEquals("from-header", lookup.getResponseEtag());
        assertNull(lookup.getResults()[0].getEtag());
    }

    // ================ Regression: no Etag header when caller did not set one ================

    @Test
    public void testBusinessSummaryByKeyOmitsEtagHeaderWhenUnset() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessSummaryResponse[0]));

        client.sendBusinessSummary("1");

        assertFalse(capturingSender.getRequest().getHeaders().containsKey("Etag"));
    }

    @Test
    public void testBusinessDetailByIdOmitsEtagHeaderWhenUnset() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new BusinessDetailResponse[0]));

        client.sendBusinessDetail("ABC123");

        assertFalse(capturingSender.getRequest().getHeaders().containsKey("Etag"));
    }

    @Test
    public void testEmptyRequestEtagIsNotSentAsHeader() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new PrincipalResponse[]{new PrincipalResponse()}));

        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup();
        lookup.setSmartyKey("1");
        lookup.setRequestEtag("");
        client.sendPropertyPrincipal(lookup);

        assertFalse(capturingSender.getRequest().getHeaders().containsKey("Etag"));
    }

    // ================ End-to-end 304 flow through StatusCodeSender ================

    @Test
    public void testBusinessDetail304ThrowsNotModifiedWithResponseEtag() {
        Headers headers = new Headers.Builder().add("Etag", "refreshed-etag").build();
        Sender pipeline = new StatusCodeSender(new MockSender(new Response(304, null, headers)));
        Client client = new Client(pipeline, new SmartySerializer());

        BusinessDetailLookup lookup = new BusinessDetailLookup("ABC");
        NotModifiedException ex = assertThrows(NotModifiedException.class,
                () -> client.sendBusinessDetail(lookup));
        assertEquals("refreshed-etag", ex.getResponseEtag());
    }

    // ================ Custom parameters on the common-path lookups ================

    @Test
    public void testCustomParameterOnPropertyPrincipalLookup() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        URLPrefixSender sender = new URLPrefixSender("http://localhost:8080", capturingSender);
        Client client = new Client(sender, new FakeSerializer(new PrincipalResponse[]{new PrincipalResponse()}));

        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup();
        lookup.setSmartyKey("1");
        lookup.addCustomParameter("experimental", "yes");
        client.sendPropertyPrincipal(lookup);

        assertEquals("http://localhost:8080/1/property/principal?experimental=yes",
                capturingSender.getRequest().getUrl());
    }
}
