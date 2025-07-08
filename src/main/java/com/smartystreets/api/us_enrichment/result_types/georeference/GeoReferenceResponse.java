package com.smartystreets.api.us_enrichment.result_types.georeference;

import com.smartystreets.api.us_enrichment.result_types.Result;

public class GeoReferenceResponse extends Result {
    private GeoReferenceAttributes attributes;

    public GeoReferenceAttributes getAttributes() {
        return attributes;
    }
}
