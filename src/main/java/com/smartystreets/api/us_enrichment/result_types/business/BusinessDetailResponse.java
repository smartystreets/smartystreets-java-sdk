package com.smartystreets.api.us_enrichment.result_types.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_enrichment.result_types.EnrichmentToStringer;

public class BusinessDetailResponse extends EnrichmentToStringer {
    @JsonProperty("smarty_key")
    private String smartyKey;
    @JsonProperty("data_set_name")
    private String dataSetName;
    @JsonProperty("business_id")
    private String businessId;
    @JsonProperty("attributes")
    private BusinessAttributes attributes;

    public String getSmartyKey() {
        return smartyKey;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public BusinessAttributes getAttributes() {
        return attributes;
    }
}
