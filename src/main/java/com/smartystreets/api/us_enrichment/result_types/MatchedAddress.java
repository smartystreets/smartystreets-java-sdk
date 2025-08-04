package com.smartystreets.api.us_enrichment.result_types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartystreets.api.us_enrichment.result_types.EnrichmentToStringer;

public class MatchedAddress extends EnrichmentToStringer {
    public String street;
    public String city;
    public String state;
    public String zipcode;
}
