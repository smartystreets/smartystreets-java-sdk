package com.smartystreets.api.us_enrichment.lookup_types.business;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.AddressSearch;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessSummaryResponse;

import java.io.IOException;

public class BusinessSummaryLookup extends Lookup {
    private BusinessSummaryResponse[] results;

    public BusinessSummaryLookup() {
    }

    public BusinessSummaryLookup(String smartyKey) {
        super(smartyKey, null);
    }

    public BusinessSummaryLookup(AddressSearch addressSearch) {
        super(addressSearch, businessDataSet, emptyDataSubset);
    }

    public BusinessSummaryResponse[] getResults() {
        return results;
    }

    public void setResults(BusinessSummaryResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException {
        this.results = serializer.deserialize(payload, BusinessSummaryResponse[].class);
    }

    @Override
    public String getDataSetName() {
        return businessDataSet;
    }

    @Override
    public String getDataSubsetName() {
        return emptyDataSubset;
    }
}
