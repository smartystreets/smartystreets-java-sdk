package com.smartystreets.api.us_enrichment.lookup_types;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class EnrichmentLookupBase {
    private String includeFields;
    private String excludeFields;
    private String requestEtag;
    private String responseEtag;
    private final Map<String, String> customParameters = new LinkedHashMap<>();

    public String getIncludeFields() {
        return this.includeFields;
    }

    public void setIncludeFields(String includeFields) {
        this.includeFields = includeFields;
    }

    public String getExcludeFields() {
        return this.excludeFields;
    }

    public void setExcludeFields(String excludeFields) {
        this.excludeFields = excludeFields;
    }

    public String getRequestEtag() {
        return this.requestEtag;
    }

    public void setRequestEtag(String requestEtag) {
        this.requestEtag = requestEtag;
    }

    public String getResponseEtag() {
        return this.responseEtag;
    }

    public void setResponseEtag(String responseEtag) {
        this.responseEtag = responseEtag;
    }

    public void addCustomParameter(String parameter, String value) {
        this.customParameters.put(parameter, value);
    }

    public Map<String, String> getCustomParameters() {
        return this.customParameters;
    }

    public abstract void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException, SmartyException;
}
