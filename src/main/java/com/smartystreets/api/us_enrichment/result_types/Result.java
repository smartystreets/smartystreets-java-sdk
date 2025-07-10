package com.smartystreets.api.us_enrichment.result_types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result extends EnrichmentToStringer {
    @JsonProperty("smarty_key")
    private String smartyKey;
    @JsonProperty("data_set_name")
    private String datasetName;
    @JsonProperty("data_subset_name")
    private String dataSubsetName;
    @JsonProperty("matched_address")
    private MatchedAddress matchedAddress;

    private String etag;

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSmartyKey() {
        return smartyKey;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getDataSubsetName() {
        return dataSubsetName;
    }

    public MatchedAddress getMatchedAddress() {
        return matchedAddress;
    }

    public String getEtag() {
        return etag;
    }

}
