package com.smartystreets.api.us_enrichment.lookup_types.georeference;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;
import com.smartystreets.api.us_enrichment.result_types.property_principal.PrincipalResponse;
import okhttp3.Headers;

import java.io.IOException;

import static com.smartystreets.api.us_enrichment.lookup_types.Lookup.emptyDataSubset;
import static com.smartystreets.api.us_enrichment.lookup_types.Lookup.geoReferenceDataSet;

public class GeoReferenceLookup extends Lookup {
    private GeoReferenceResponse[] results;

    public GeoReferenceLookup(String smartyKey, String etag) {
        super(smartyKey, etag);
    }

    public GeoReferenceResponse[] getResults() {
        return results;
    }

    public void setResults(GeoReferenceResponse[] results) {
        this.results = results;
    }

    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload, Headers headers) throws IOException {
        this.results = serializer.deserialize(payload, GeoReferenceResponse[].class, headers);
        if (headers != null) {
            this.results[0].setEtag(headers.get("etag"));
        }
    }

    public String getDataSet() {
        return geoReferenceDataSet;
    }

    public String getDataSubset() {
        return emptyDataSubset;
    }


}
