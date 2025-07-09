package com.smartystreets.api.us_enrichment.lookup_types.secondary;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.AddressSearch;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;
import okhttp3.Headers;
import java.io.IOException;

public class SecondaryLookup extends Lookup {
    private SecondaryResponse[] results;

    public SecondaryLookup() {
    }

    // legacy constructor - we recommend using the empty constructor and set parameters afterward
    public SecondaryLookup(String smartyKey) {
        super(smartyKey, "");
    }

    // legacy constructor - we recommend using the empty constructor and set parameters afterward
    public SecondaryLookup(AddressSearch addressSearch) {
        super(addressSearch, "", "");
    }

    public SecondaryResponse[] getResults() {
        return results;
    }

    public void setResults(SecondaryResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload, Headers headers) throws IOException {
        this.results = serializer.deserialize(payload, SecondaryResponse[].class, headers);
        if (headers != null) {
            this.results[0].setEtag(headers.get("etag"));
        }
    }

    @Override
    public String getDataSetName() {
        return secondaryDataSet;
    }

    @Override
    public String getDataSubsetName() {
        return emptyDataSubset;
    }
}
