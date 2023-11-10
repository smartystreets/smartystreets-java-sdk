package com.smartystreets.api.us_enrichment;

public class Lookup {
    private String smartyKey;
    private String datasetName;
    private String dataSubsetName;
    private Result result;

    public Lookup(String smartyKey, String datasetName, String dataSubsetName) {
        this.smartyKey = smartyKey;
        this.datasetName = datasetName;
        this.dataSubsetName = dataSubsetName;
    }

    public Result getResults() {
        return result;
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

    public void setResults(Result result) {
        this.result = result;
    }
}
