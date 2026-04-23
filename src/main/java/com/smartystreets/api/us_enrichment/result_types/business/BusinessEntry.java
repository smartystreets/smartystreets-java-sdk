package com.smartystreets.api.us_enrichment.result_types.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_enrichment.result_types.EnrichmentToStringer;

public class BusinessEntry extends EnrichmentToStringer {
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("business_id")
    private String businessId;

    public String getCompanyName() {
        return companyName;
    }

    public String getBusinessId() {
        return businessId;
    }
}
