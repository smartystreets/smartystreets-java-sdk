package com.smartystreets.api.us_enrichment.result_types.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_enrichment.result_types.EnrichmentToStringer;

public class BusinessSummaryResponse extends EnrichmentToStringer {
    @JsonProperty("smarty_key")
    private String smartyKey;
    @JsonProperty("data_set_name")
    private String dataSetName;
    @JsonProperty("businesses")
    private BusinessEntry[] businesses;

    public String getSmartyKey() {
        return smartyKey;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public BusinessEntry[] getBusinesses() {
        return businesses;
    }
}
