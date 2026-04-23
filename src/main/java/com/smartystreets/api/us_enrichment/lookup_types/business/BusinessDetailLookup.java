package com.smartystreets.api.us_enrichment.lookup_types.business;

import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.lookup_types.EnrichmentLookupBase;
import com.smartystreets.api.us_enrichment.result_types.business.BusinessDetailResponse;

import java.io.IOException;

public class BusinessDetailLookup extends EnrichmentLookupBase {
    private String businessId;
    private BusinessDetailResponse result;

    public BusinessDetailLookup() {
    }

    public BusinessDetailLookup(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public BusinessDetailResponse getResult() {
        return this.result;
    }

    public void setResult(BusinessDetailResponse result) {
        this.result = result;
    }

    // The detail endpoint returns a one-element array on success; >1 element is a server-contract violation.
    @Override
    public void deserializeAndSetResults(Serializer serializer, byte[] payload) throws IOException, SmartyException {
        BusinessDetailResponse[] results = serializer.deserialize(payload, BusinessDetailResponse[].class);
        if (results == null || results.length == 0) {
            this.result = null;
            return;
        }
        if (results.length > 1) {
            throw new SmartyException("business detail response contained " + results.length + " results; expected at most 1");
        }
        this.result = results[0];
    }
}
