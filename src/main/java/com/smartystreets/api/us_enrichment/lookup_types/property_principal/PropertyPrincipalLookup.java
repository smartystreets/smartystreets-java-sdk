package com.smartystreets.api.us_enrichment.lookup_types.property_principal;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.AddressSearch;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import okhttp3.Headers;

import java.io.IOException;

public class PropertyPrincipalLookup extends Lookup {
    private PrincipalResponse[] results;

    public PropertyPrincipalLookup() {
    }

    @Deprecated
    public PropertyPrincipalLookup(String smartyKey, String include, String exclude, String etag) {
        super(smartyKey, include, exclude, etag);
    }

    public PropertyPrincipalLookup(AddressSearch addressSearch) {
        super(addressSearch, "property", "principal");
    }

    public PrincipalResponse[] getResults() {
        return results;
    }

    public void setResults(PrincipalResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload, Headers headers) throws IOException {
        this.results = serializer.deserialize(payload, PrincipalResponse[].class, headers);
        if (headers != null) {
            this.results[0].setEtag(headers.get("etag"));
        }
    }

    public String getDataSet() {
        return propertyDataSet;
    }

    public String getDataSubset() {
        return principalDataSubset;
    }

}
