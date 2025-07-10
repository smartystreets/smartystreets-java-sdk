package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_financial.PropertyFinancialLookup;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.lookup_types.risk.RiskLookup;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskResponse;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryCountLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryLookup;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.io.UnsupportedEncodingException;

public class Client {
    private Sender sender;
    private Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public FinancialResponse[] sendPropertyFinancial(PropertyFinancialLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    //sendPropertyFinancialLookup is deprecated, use sendPropertyFinancial
    public FinancialResponse[] sendPropertyFinancialLookup(AddressSearch addressSearch) throws SmartyException, IOException, InterruptedException {
        PropertyFinancialLookup lookup = new PropertyFinancialLookup(addressSearch);
        send(lookup);
        return lookup.getResults();
    }

    public PrincipalResponse[] sendPropertyPrincipal(PropertyPrincipalLookup lookup) throws SmartyException, IOException, InterruptedException {
        send(lookup);
        return lookup.getResults();
    }

    //sendPropertyPrincipalLookup is deprecated, use sendPropertyPrincipal
    public PrincipalResponse[] sendPropertyPrincipalLookup(String smartyKey) throws SmartyException, IOException, InterruptedException {
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup(smartyKey,"","","");
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

    private void send(Lookup lookup) throws IOException, SmartyException, InterruptedException {
        if (lookup == null || lookup.getSmartyKey() == null || lookup.getSmartyKey().isEmpty()) {
            if (lookup == null || lookup.getAddressSearch() == null) {
                throw new SmartyException("Client.send() requires a Lookup with the 'smartyKey' field set or an address search.");
            }
        }

        Request request = this.buildRequest(lookup);
        Response response = this.sender.send(request);

        lookup.deserializeAndSetResults(serializer, response.getPayload(), response.getHeaders());
    }

    private Request buildRequest(Lookup lookup) throws UnsupportedEncodingException {
        Request request = new Request();
        String dataSetUrl;

        request.getHeaders().put("Etag", lookup.getEtag());

        if (lookup.getDataSubsetName() == "") {
            dataSetUrl = lookup.getDataSetName();
        } else {
            dataSetUrl = lookup.getDataSetName() + "/" + lookup.getDataSubsetName();
        }

        if (lookup.getSmartyKey() == null || lookup.getSmartyKey().isEmpty()) {
            request.setUrlComponents("/search/" + dataSetUrl);
            request.putParameter("freeform", lookup.getAddressSearch().freeform());
            request.putParameter("street", lookup.getAddressSearch().street());
            request.putParameter("city", lookup.getAddressSearch().city());
            request.putParameter("state", lookup.getAddressSearch().state());
            request.putParameter("zipcode", lookup.getAddressSearch().zipcode());
        } else {
            request.setUrlComponents("/" + lookup.getSmartyKey() + "/" + dataSetUrl);
        }

        if (lookup.getInclude() != "") {
            request.putParameter("include", lookup.getInclude());
        }
        if (lookup.getExclude() != "") {
            request.putParameter("exclude", lookup.getExclude());
        }

        return request;
    }
}
