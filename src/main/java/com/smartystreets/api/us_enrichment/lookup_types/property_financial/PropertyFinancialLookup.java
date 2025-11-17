package com.smartystreets.api.us_enrichment.lookup_types.property_financial;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.result_types.AddressSearch;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;
import okhttp3.Headers;

import java.io.IOException;

public class PropertyFinancialLookup extends Lookup {
    private FinancialResponse[] results;

    public PropertyFinancialLookup() {
    }

    // legacy constructor - we recommend using the empty constructor and set parameters afterward
    public PropertyFinancialLookup(String smartyKey, String include, String exclude, String eTag) {
        super(smartyKey, include, exclude, eTag);
    }

    // legacy constructor - we recommend using the empty constructor and set parameters afterward
    public PropertyFinancialLookup(AddressSearch addressSearch) {
        super(addressSearch, "", "");
    }

    public FinancialResponse[] getResults() {
        return results;
    }

    public void setResults(FinancialResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload, Headers headers) throws IOException {
        this.results = serializer.deserialize(payload, FinancialResponse[].class, headers);
        if (headers != null) {
            this.results[0].setEtag(headers.get("etag"));
        }
    }

    @Override
    public String getDataSetName() {
        return propertyDataSet;
    }

    @Override
    public String getDataSubsetName() {
        return financialDataSubset;
    }
}
