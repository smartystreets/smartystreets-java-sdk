package com.smartystreets.api.us_enrichment.result_types.georeference;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_enrichment.result_types.Result;

public class GeoReferenceResponse extends Result {
    @JsonProperty("data_set_version")
    private String dataSetVersion;

    private GeoReferenceAttributes attributes;
    public GeoReferenceAttributes getAttributes() {
        return attributes;
    }

    public String getDataSetVersion() {
        return this.dataSetVersion;
    }
}
