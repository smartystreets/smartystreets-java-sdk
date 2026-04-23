package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.lookup_types.EnrichmentLookupBase;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
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

import java.io.Closeable;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Client implements Closeable {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    //sendPropertyPrincipalLookup is deprecated, use sendPropertyPrincipal
    public PrincipalResponse[] sendPropertyPrincipal(PropertyPrincipalLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    //sendPropertyPrincipalLookup is deprecated, use sendPropertyPrincipal
    public PrincipalResponse[] sendPropertyPrincipalLookup(String smartyKey) throws SmartyException, IOException, InterruptedException {
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup(smartyKey, "", "", "");
        send(lookup);
        return lookup.getResults();
    }

    //sendPropertyPrincipalLookup is deprecated, use sendPropertyPrincipal
    public PrincipalResponse[] sendPropertyPrincipalLookup(AddressSearch addressSearch) throws SmartyException, IOException, InterruptedException {
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup(addressSearch);
        send(lookup);
        return lookup.getResults();
    }

    public GeoReferenceResponse[] sendGeoReference(GeoReferenceLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    public RiskResponse[] sendRisk(RiskLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    public SecondaryResponse[] sendSecondary(SecondaryLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    //sendSecondaryLookup is deprecated, use sendSecondary
    public SecondaryResponse[] sendSecondaryLookup(String smartyKey) throws SmartyException, IOException, InterruptedException {
        SecondaryLookup lookup = new SecondaryLookup(smartyKey);
        send(lookup);
        return lookup.getResults();
    }

    //sendSecondaryLookup is deprecated, use sendSecondary
    public SecondaryResponse[] sendSecondaryLookup(AddressSearch addressSearch) throws SmartyException, IOException, InterruptedException {
        SecondaryLookup lookup = new SecondaryLookup(addressSearch);
        send(lookup);
        return lookup.getResults();
    }

    public SecondaryCountResponse[] sendSecondaryCount(SecondaryCountLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    //sendSecondaryCountLookup is deprecated, use sendSecondaryCount
    public SecondaryCountResponse[] sendSecondaryCountLookup(String smartKey) throws SmartyException, IOException, InterruptedException {
        SecondaryCountLookup lookup = new SecondaryCountLookup(smartKey);
        send(lookup);
        return lookup.getResults();
    }

    //sendSecondaryCountLookup is deprecated, use sendSecondaryCount
    public SecondaryCountResponse[] sendSecondaryCountLookup(AddressSearch addressSearch) throws SmartyException, IOException, InterruptedException {
        SecondaryCountLookup lookup = new SecondaryCountLookup(addressSearch);
        send(lookup);
        return lookup.getResults();
    }

    public BusinessSummaryResponse[] sendBusinessSummary(String smartyKey) throws SmartyException, IOException, InterruptedException {
        BusinessSummaryLookup lookup = new BusinessSummaryLookup(smartyKey);
        send(lookup);
        return lookup.getResults();
    }

    public BusinessSummaryResponse[] sendBusinessSummary(BusinessSummaryLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    public BusinessDetailResponse sendBusinessDetail(String businessId) throws SmartyException, IOException, InterruptedException {
        BusinessDetailLookup lookup = new BusinessDetailLookup(businessId);
        dispatchBusinessDetail(lookup);
        return lookup.getResult();
    }

    public BusinessDetailResponse sendBusinessDetail(BusinessDetailLookup lookup) throws SmartyException, IOException, InterruptedException {
        dispatchBusinessDetail(lookup);
        return lookup.getResult();
    }

    private void send(Lookup lookup) throws IOException, SmartyException, InterruptedException {
        if (lookup == null) {
            throw new SmartyException("Client.send() requires a Lookup with the 'smartyKey' field set or an address search.");
        }
        if (isBlank(lookup.getSmartyKey()) && !hasAddressSearchContent(lookup.getAddressSearch())) {
            throw new SmartyException("Client.send() requires a Lookup with the 'smartyKey' field set or an address search.");
        }

        Request request = buildRequest(lookup);
        dispatch(request, lookup);
    }

    private void dispatchBusinessDetail(BusinessDetailLookup lookup) throws IOException, SmartyException, InterruptedException {
        if (lookup == null || isBlank(lookup.getBusinessId())) {
            throw new SmartyException("BusinessDetailLookup requires a non-empty 'businessId'");
        }

        Request request = new Request();
        request.setUrlComponents("/business/" + URLEncoder.encode(lookup.getBusinessId(), StandardCharsets.UTF_8));
        applyCommonRequestFields(request, lookup);

        dispatch(request, lookup);
    }

    private void dispatch(Request request, EnrichmentLookupBase lookup) throws IOException, SmartyException, InterruptedException {
        Response response = this.sender.send(request);

        String etag = response.getEtag();
        if (etag != null) {
            lookup.setResponseEtag(etag);
        }

        lookup.deserializeAndSetResults(this.serializer, response.getPayload());
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();
        String dataSetUrl;

        if (isBlank(lookup.getDataSubsetName())) {
            dataSetUrl = lookup.getDataSetName();
        } else {
            dataSetUrl = lookup.getDataSetName() + "/" + lookup.getDataSubsetName();
        }

        if (isBlank(lookup.getSmartyKey())) {
            request.setUrlComponents("/search/" + dataSetUrl);
            AddressSearch search = lookup.getAddressSearch();
            request.putParameter("freeform", search.freeform());
            request.putParameter("street", search.street());
            request.putParameter("city", search.city());
            request.putParameter("state", search.state());
            request.putParameter("zipcode", search.zipcode());
        } else {
            request.setUrlComponents("/" + lookup.getSmartyKey() + "/" + dataSetUrl);
        }

        applyCommonRequestFields(request, lookup);

        if (!isBlank(lookup.getFeatures())) {
            request.putParameter("features", lookup.getFeatures());
        }

        return request;
    }

    private static void applyCommonRequestFields(Request request, EnrichmentLookupBase lookup) {
        if (!isBlank(lookup.getIncludeFields())) {
            request.putParameter("include", lookup.getIncludeFields());
        }
        if (!isBlank(lookup.getExcludeFields())) {
            request.putParameter("exclude", lookup.getExcludeFields());
        }
        if (!isBlank(lookup.getRequestEtag())) {
            request.getHeaders().put("Etag", lookup.getRequestEtag());
        }
        for (Map.Entry<String, String> entry : lookup.getCustomParameters().entrySet()) {
            request.putParameter(entry.getKey(), entry.getValue());
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static boolean hasAddressSearchContent(AddressSearch search) {
        if (search == null) return false;
        return !isBlank(search.freeform()) || !isBlank(search.street());
    }

    @Override
    public void close() throws IOException {
        this.sender.close();
    }
}
