package com.smartystreets.api.us_enrichment.lookup_types;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.us_enrichment.lookup_types.georeference.GeoReferenceLookup;
import okhttp3.Headers;

import java.io.IOException;
import java.util.Map;

public abstract class Lookup {
    public static final String financialDataSubset = "financial";
    public static final String principalDataSubset = "principal";
    public static final String propertyDataSet = "property";
    public static final String geoReferenceDataSet = "geo-reference";
    public static final String emptyDataSubset = "";

    private String smartyKey;
    private String eTag;
    private String include;
    private String exclude;

    public Lookup(String smartyKey, String include, String exclude, String eTag) {
        this.smartyKey = smartyKey;
        this.include = include;
        this.exclude = exclude;
        this.eTag = eTag;
    }

    public Lookup(String smartyKey, String eTag) {
        this.smartyKey = smartyKey;
        this.eTag = eTag;
    }

    public String getSmartyKey() {
        return smartyKey;
    }


    public String getInclude() {
        return include;
    }

    public String getExclude() {
        return exclude;
    }

    public String getEtag() {
        return eTag;
    }

    public abstract void deserializeAndSetResults(Serializer serializer, byte[] payload, Headers headers) throws IOException;


    public abstract String getDataSet();

    public abstract String getDataSubset();


    interface EnrichmentLookup {
        String getSmartyKey();

        String getDataSet();

        String getDataSubset();

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

    public void setEtag(String etag) {
        this.eTag = etag;
    }
}
