package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.us_enrichment.result_types.Attributes;

public class Result {
    private String smartyKey;
    private String datasetName;
    private String dataSubsetName;
    private Attributes attributes;

    public String getSmartyKey() {
        return smartyKey;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getDataSubsetName() {
        return dataSubsetName;
    }

    public Attributes getAttributes() {
        return attributes;
    }
}

