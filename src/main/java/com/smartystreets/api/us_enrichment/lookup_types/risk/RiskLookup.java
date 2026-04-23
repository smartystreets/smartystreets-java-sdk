package com.smartystreets.api.us_enrichment.lookup_types.risk;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.risk.RiskResponse;

import java.io.IOException;

public class RiskLookup extends Lookup {
    private RiskResponse[] results;

    public RiskLookup() {
    }

    public RiskResponse[] getResults() {
        return results;
    }

    public void setResults(RiskResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException {
        this.results = serializer.deserialize(payload, RiskResponse[].class);
    }

    @Override
    public String getDataSetName() {
        return riskDataSet;
    }

    @Override
    public String getDataSubsetName() {
        return emptyDataSubset;
    }

}
