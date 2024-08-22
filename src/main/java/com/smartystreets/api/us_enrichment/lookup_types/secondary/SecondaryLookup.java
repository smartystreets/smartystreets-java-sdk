package com.smartystreets.api.us_enrichment.lookup_types.secondary;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.secondary.SecondaryResponse;

import java.io.IOException;

public class SecondaryLookup extends Lookup {

    private SecondaryResponse[] results;

    public SecondaryLookup(String smartyKey) {
        super(smartyKey, "secondary", "");
    }

    public SecondaryResponse[] getResults() {
        return results;
    }

    public void setResults(SecondaryResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException {
        this.results = serializer.deserialize(payload, SecondaryResponse[].class);
    }
}
