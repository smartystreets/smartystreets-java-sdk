package com.smartystreets.api.us_enrichment.lookup_types;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.AddressSearch;

import java.io.IOException;

public abstract class Lookup {
    private String smartyKey;
    private final String datasetName;
    private final String dataSubsetName;
    private AddressSearch addressSearch;

    public Lookup(String smartyKey, String datasetName, String dataSubsetName) {
        this.smartyKey = smartyKey;
        this.datasetName = datasetName;
        this.dataSubsetName = dataSubsetName;
    }

    public Lookup(AddressSearch search, String datasetName, String dataSubsetName) {
        this.addressSearch = search;
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

    public AddressSearch getAddressSearch() { return addressSearch; }

    public abstract void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException;

}
