package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_financial.PropertyFinancialLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private Sender sender;
    private Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    //sendPropertyFinancialLookup is deprecated, rerouting to sendPropertyFinancial


    public FinancialResponse[] sendPropertyFinancial(PropertyFinancialLookup lookup) throws SmartyException, IOException, InterruptedException {
        PropertyFinancialLookup financialLookup = new PropertyFinancialLookup(lookup.getSmartyKey(), lookup.getInclude(), lookup.getExclude(), lookup.getEtag());
        send(financialLookup);
        return financialLookup.getResults();
    }

    //sendPropertyFinancialLookup is deprecated, rerouting to sendPropertyFinancial

    public PrincipalResponse[] sendPropertyPrincipal(PropertyPrincipalLookup lookup) throws SmartyException, IOException, InterruptedException {
        PropertyPrincipalLookup principalLookup = new PropertyPrincipalLookup(lookup.getSmartyKey(), lookup.getInclude(), lookup.getExclude(), lookup.getEtag());
        send(principalLookup);
        return principalLookup.getResults();
    }

    public GeoReferenceResponse[] sendGeoReference(GeoReferenceLookup lookup) throws SmartyException, IOException, InterruptedException {
        GeoReferenceLookup geoReferenceLookup = new GeoReferenceLookup(lookup.getSmartyKey(), lookup.getEtag());
        send(geoReferenceLookup);
        return geoReferenceLookup.getResults();
    }

    private void send(Lookup lookup) throws IOException, SmartyException, InterruptedException {
        if (lookup == null || lookup.getSmartyKey() == null || lookup.getSmartyKey().isEmpty())
            throw new SmartyException("Client.send() requires a Lookup with the 'smartyKey' field set");

        Request request = this.buildRequest(lookup);
        Response response = this.sender.send(request);

        lookup.deserializeAndSetResults(serializer, response.getPayload(), response.getHeaders());
    }
    //Current issues: I don't know what to do with the include and exclude parameters. How to add them to the query parameters.


    private Request buildRequest(Lookup lookup) {
        Request request = new Request();
        if (lookup.getDataSubset() != "") {
            request.setUrlPrefix("/" + lookup.getSmartyKey() + "/" + lookup.getDataSet() + "/" + lookup.getDataSubset());

        } else {
            request.setUrlPrefix("/" + lookup.getSmartyKey() + "/" + lookup.getDataSet());

        }
        request.getHeaders().put("Etag", lookup.getEtag());
        return request;
    }
}
