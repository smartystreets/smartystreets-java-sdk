package com.smartystreets.api.us_enrichment.result_types.property_financial;

import com.smartystreets.api.us_enrichment.result_types.Result;

public class FinancialResponse extends Result {
    private FinancialAttributes attributes;

    public FinancialAttributes getAttributes() {
        return attributes;
    }
}
