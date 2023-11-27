package com.smartystreets.api.us_enrichment.lookup_types.property_financial;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.property_financial.FinancialResponse;

import java.io.IOException;

public class PropertyFinancialLookup extends Lookup {
    private FinancialResponse[] results;
    public PropertyFinancialLookup(String smartyKey) {
        super(smartyKey, "property", "financial");
    }

    public FinancialResponse[] getResults() {
        return results;
    }

    public void setResults(FinancialResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException {
        this.results = serializer.deserialize(payload, FinancialResponse[].class);
    }
}
