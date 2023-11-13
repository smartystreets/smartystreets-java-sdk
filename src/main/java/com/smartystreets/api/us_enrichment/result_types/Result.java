package com.smartystreets.api.us_enrichment.result_types;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty("smarty_key")
    private String smartyKey;
    @JsonProperty("data_set_name")
    private String datasetName;
    @JsonProperty("data_subset_name")
    private String dataSubsetName;

    public String getSmartyKey() {
        return smartyKey;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getDataSubsetName() {
        return dataSubsetName;
    }

}

