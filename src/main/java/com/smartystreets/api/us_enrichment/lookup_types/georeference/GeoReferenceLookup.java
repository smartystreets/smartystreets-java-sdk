package com.smartystreets.api.us_enrichment.lookup_types.georeference;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.lookup_types.Lookup;
import com.smartystreets.api.us_enrichment.result_types.georeference.GeoReferenceResponse;

import java.io.IOException;

import static com.smartystreets.api.us_enrichment.lookup_types.Lookup.emptyDataSubset;
import static com.smartystreets.api.us_enrichment.lookup_types.Lookup.geoReferenceDataSet;

public class GeoReferenceLookup extends Lookup {
    private GeoReferenceResponse[] results;
    private String subset;

    public GeoReferenceLookup(String subset) {
        this.subset = subset;
    }

    // This legacy constructor is inadequate as it will only query the latest census version of the data because there is not a way to set the data subset.
    // Use GeoReferenceLookup(String subset) to set the data subset then set all other applicable lookup fields manually.
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
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException {
        this.results = serializer.deserialize(payload, GeoReferenceResponse[].class);
    }

    @Override
    public String getDataSetName() {
        return geoReferenceDataSet;
    }

    @Override
    public String getDataSubsetName() {
        if (this.subset == null) {
            this.subset = "";
        }
        return this.subset;
    }

}
