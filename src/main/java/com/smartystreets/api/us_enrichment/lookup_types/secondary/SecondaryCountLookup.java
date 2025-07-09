package com.smartystreets.api.us_enrichment.lookup_types.secondary;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.AddressSearch;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;

import java.io.IOException;

public class SecondaryCountLookup extends Lookup {

    private SecondaryCountResponse[] results;

    public SecondaryCountLookup() {
    }

    @Deprecated
    public SecondaryCountLookup(String smartyKey) {
        super(smartyKey, "secondary", "count");
    }

    @Deprecated
    public SecondaryCountLookup(AddressSearch addressSearch) {
        super(addressSearch, "secondary", "count");
    }

    public SecondaryCountResponse[] getResults() {
        return results;
    }

    public void setResults(SecondaryCountResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException {
        this.results = serializer.deserialize(payload, SecondaryCountResponse[].class);
    }

    public String getDataSet() {
        return secondaryDataSet;
    }

    public String getDataSubset() {
        return secondaryCountDataSubset;
    }

}
