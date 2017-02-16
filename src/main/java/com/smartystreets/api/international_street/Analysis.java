package com.smartystreets.api.international_street;

import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/international-street-api#analysis"
 */
public class Analysis {
    @Key("verification_status")
    private String verificationStatus;

    @Key("address_precision")
    private String addressPrecision;

    @Key("max_address_precision")
    private String maxAddressPrecision;


    public String getVerificationStatus() {
        return verificationStatus;
    }

    public String getAddressPrecision() {
        return addressPrecision;
    }

    public String getMaxAddressPrecision() {
        return maxAddressPrecision;
    }
}
