package com.smartystreets.api.us_enrichment.lookup_types;

import com.smartystreets.api.us_enrichment.result_types.AddressSearch;

public abstract class Lookup extends EnrichmentLookupBase {
    private String smartyKey;
    private AddressSearch addressSearch;
    private String features;

    public static final String propertyDataSet = "property";
    public static final String financialDataSubset = "financial";
    public static final String principalDataSubset = "principal";
    public static final String geoReferenceDataSet = "geo-reference";
    public static final String riskDataSet = "risk";
    public static final String secondaryDataSet = "secondary";
    public static final String secondaryCountDataSubset = "count";
    public static final String businessDataSet = "business";
    public static final String emptyDataSubset = "";

    public Lookup() {
    }

    public Lookup(String smartyKey, String include, String exclude, String eTag) {
        this.smartyKey = smartyKey;
        setIncludeFields(include);
        setExcludeFields(exclude);
        setRequestEtag(eTag);
    }

    public Lookup(String smartyKey, String eTag) {
        this.smartyKey = smartyKey;
        setRequestEtag(eTag);
    }

    // datasetName/dataSubsetName are ignored; the concrete subclass supplies them via getDataSetName()/getDataSubsetName().
    public Lookup(AddressSearch search, String datasetName, String dataSubsetName) {
        this.addressSearch = search;
    }

    public String getSmartyKey() {
        return this.smartyKey;
    }

    public String getInclude() {
        return getIncludeFields();
    }

    public String getExclude() {
        return getExcludeFields();
    }

    public String getFeatures() {
        return this.features;
    }

    public AddressSearch getAddressSearch() {
        return this.addressSearch;
    }

    public abstract String getDataSetName();
    public abstract String getDataSubsetName();

    public void setSmartyKey(String smartyKey) {
        this.smartyKey = smartyKey;
    }

    public void setInclude(String include) {
        setIncludeFields(include);
    }

    public void setExclude(String exclude) {
        setExcludeFields(exclude);
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setAddressSearch(AddressSearch search) {
        this.addressSearch = search;
    }
}
