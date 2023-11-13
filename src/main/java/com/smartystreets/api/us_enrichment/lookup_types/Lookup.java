package com.smartystreets.api.us_enrichment.lookup_types;

import com.smartystreets.api.Serializer;

import java.io.IOException;

public abstract class Lookup {
    private final String smartyKey;
    private final String datasetName;
    private final String dataSubsetName;

    public Lookup(String smartyKey, String datasetName, String dataSubsetName) {
        this.smartyKey = smartyKey;
        this.datasetName = datasetName;
        this.dataSubsetName = dataSubsetName;
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

    public abstract void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException;

}
