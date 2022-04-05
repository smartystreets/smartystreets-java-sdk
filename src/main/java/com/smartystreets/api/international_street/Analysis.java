package com.smartystreets.api.international_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/international-street-api#analysis"
 */
public class Analysis implements Serializable {
    private String verificationStatus;
    private String addressPrecision;
    private String maxAddressPrecision;
    private Changes changes;


    @JsonProperty("verification_status")
    public String getVerificationStatus() {
        return verificationStatus;
    }

    @JsonProperty("address_precision")
    public String getAddressPrecision() {
        return addressPrecision;
    }

    @JsonProperty("max_address_precision")
    public String getMaxAddressPrecision() {
        return maxAddressPrecision;
    }

    public Changes getChanges() {
        return changes;
    }
}
