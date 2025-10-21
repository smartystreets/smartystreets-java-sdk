package com.smartystreets.api.us_enrichment.result_types.risk;

import com.smartystreets.api.us_enrichment.result_types.Result;

public class RiskResponse extends Result {
    private RiskAttributes attributes;

    public RiskAttributes getAttributes() {
        return attributes;
    }
}
