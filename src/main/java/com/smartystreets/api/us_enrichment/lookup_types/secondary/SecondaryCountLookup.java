package com.smartystreets.api.us_enrichment.lookup_types.secondary;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.result_types.AddressSearch;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryCountResponse;
import okhttp3.Headers;
import java.io.IOException;

public class SecondaryCountLookup extends Lookup {

    private SecondaryCountResponse[] results;

    public SecondaryCountLookup() {
    }

    // legacy constructor - we recommend using the empty constructor and set parameters afterward
    public SecondaryCountLookup(String smartyKey) {
        super(smartyKey, "");
    }

    // legacy constructor - we recommend using the empty constructor and set parameters afterward
    public SecondaryCountLookup(AddressSearch addressSearch) {
        super(addressSearch, "", "");
    }

    public SecondaryCountResponse[] getResults() {
        return results;
    }

    public void setResults(SecondaryCountResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload, Headers headers) throws IOException {
        this.results = serializer.deserialize(payload, SecondaryCountResponse[].class, headers);
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
        return secondaryCountDataSubset;
    }

}
