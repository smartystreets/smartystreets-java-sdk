package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_financial.PropertyFinancialLookup;
import com.smartystreets.api.us_enrichment.lookup_types.property_principal.PropertyPrincipalLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryCountLookup;
import com.smartystreets.api.us_enrichment.lookup_types.secondary.SecondaryLookup;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Client {
    private Sender sender;
    private Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public FinancialResponse[] sendPropertyFinancialLookup(String smartyKey) throws SmartyException, IOException, InterruptedException {
        PropertyFinancialLookup lookup = new PropertyFinancialLookup(smartyKey);
        send(lookup);
        return lookup.getResults();
    }

    public FinancialResponse[] sendPropertyFinancialLookup(AddressSearch addressSearch) throws SmartyException, IOException, InterruptedException {
        PropertyFinancialLookup lookup = new PropertyFinancialLookup(addressSearch);
        send(lookup);
        return lookup.getResults();
    }

    public PrincipalResponse[] sendPropertyPrincipalLookup(String smartyKey) throws SmartyException, IOException, InterruptedException {
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup(smartyKey);
        send(lookup);
        return lookup.getResults();
    }

    public PrincipalResponse[] sendPropertyPrincipalLookup(AddressSearch addressSearch) throws SmartyException, IOException, InterruptedException {
        PropertyPrincipalLookup lookup = new PropertyPrincipalLookup(addressSearch);
        send(lookup);
        return lookup.getResults();
    }

    public SecondaryResponse[] sendSecondaryLookup(String smartKey) throws SmartyException, IOException, InterruptedException {
        SecondaryLookup lookup = new SecondaryLookup(smartKey);
        send(lookup);
        return lookup.getResults();
    }

    public SecondaryResponse[] sendSecondaryLookup(AddressSearch addressSearch) throws SmartyException, IOException, InterruptedException {
        SecondaryLookup lookup = new SecondaryLookup(addressSearch);
        send(lookup);
        return lookup.getResults();
    }

    public SecondaryCountResponse[] sendSecondaryCountLookup(String smartKey) throws SmartyException, IOException, InterruptedException {
        SecondaryCountLookup lookup = new SecondaryCountLookup(smartKey);
        send(lookup);
        return lookup.getResults();
    }

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

        lookup.deserializeAndSetResults(serializer, response.getPayload());
    }

    private Request buildRequest(Lookup lookup) throws UnsupportedEncodingException {
        Request request = new Request();
        if (lookup.getSmartyKey() == null || lookup.getSmartyKey().isEmpty()) {
            request.setUrlComponents("/search/" + lookup.getDatasetName() + "/" + lookup.getDataSubsetName() + lookup.getAddressSearch().toSearchString());
        } else {
            request.setUrlComponents("/" + lookup.getSmartyKey() + "/" + lookup.getDatasetName() + "/" + lookup.getDataSubsetName());
        }
        return request;
    }
}
