package com.smartystreets.api.us_enrichment.lookup_types;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.result_types.AddressSearch;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import okhttp3.Headers;

import java.io.IOException;
import java.util.Map;

public abstract class Lookup {
    private String smartyKey;
    private final String datasetName;    // only here to support a legacy constructor that we cannot remove
    private final String dataSubsetName; // only here to support a legacy constructor that we cannot remove
    private AddressSearch addressSearch;
    private String include;
    private String exclude;
    private String features;
    private String eTag;

    public static final String propertyDataSet = "property";
    public static final String financialDataSubset = "financial";
    public static final String principalDataSubset = "principal";
    public static final String geoReferenceDataSet = "geo-reference";
    public static final String riskDataSet = "risk";
    public static final String secondaryDataSet = "secondary";
    public static final String secondaryCountDataSubset = "count";
    public static final String emptyDataSubset = "";


    // used to instantiate a lookup then set all lookup fields using set methods
    public Lookup() {
        this.datasetName = "";
        this.dataSubsetName = "";
    }

    public Lookup(String smartyKey, String include, String exclude, String eTag) {
        this.smartyKey = smartyKey;
        this.datasetName = "";
        this.dataSubsetName = "";
        this.include = include;
        this.exclude = exclude;
        this.eTag = eTag;
    }

    public Lookup(String smartyKey, String eTag) {
        this.smartyKey = smartyKey;
        this.datasetName = "";
        this.dataSubsetName = "";
        this.eTag = eTag;
    }

    // This constructor is around for legacy purposes as datasetName and dataSubsetName are never used in this superclass
    public Lookup(AddressSearch search, String datasetName, String dataSubsetName) {
        this.addressSearch = search;
        this.datasetName = datasetName; // not used
        this.dataSubsetName = dataSubsetName; // not used
    }

    public String getSmartyKey() {
        return this.smartyKey;
    }

    public String getInclude() {
        return this.include;
    }

    public String getExclude() {
        return this.exclude;
    }

    public String getFeatures() {
        return this.features;
    }

    public String getEtag() {
        return this.eTag;
    }

    public AddressSearch getAddressSearch() {
        return addressSearch;
    }

    public abstract void deserializeAndSetResults(Serializer serializer, byte[] payload, Headers headers) throws IOException;
    public abstract String getDataSetName();
    public abstract String getDataSubsetName();

    interface EnrichmentLookup {
        String getSmartyKey();
        String getDataSetName();
        String getDataSubsetName();
        String getEtag();
        Lookup getLookup();
        void populate(Map<String, String> query);
        void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException;
    }

    public void setSmartyKey(String smartyKey) {
        this.smartyKey = smartyKey;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setEtag(String etag) {
        this.eTag = etag;
    }

    public void setAddressSearch(AddressSearch search) {
        this.addressSearch = search;
    }
}
