package com.smartystreets.api.us_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * This class contains the matched address broken down into its<br>
 *     fundamental pieces.
 * @see "https://smartystreets.com/docs/cloud/us-street-api#components"
 */
public class Components implements Serializable {

    //region [ Fields ]

    private String urbanization;
    private String primaryNumber;
    private String streetName;
    private String streetPredirection;
    private String streetPostdirection;
    private String streetSuffix;
    private String secondaryNumber;
    private String secondaryDesignator;
    private String extraSecondaryNumber;
    private String extraSecondaryDesignator;
    private String pmbDesignator;
    private String pmbNumber;
    private String cityName;
    private String defaultCityName;
    private String state;
    private String zipCode;
    private String plus4Code;
    private String deliveryPoint;
    private String deliveryPointCheckDigit;

    //endregion

    //region [ Getters ]

    @JsonProperty("urbanization")
    public String getUrbanization() {
        return this.urbanization;
    }

    @JsonProperty("primary_number")
    public String getPrimaryNumber() {
        return this.primaryNumber;
    }

    @JsonProperty("street_name")
    public String getStreetName() {
        return this.streetName;
    }

    @JsonProperty("street_predirection")
    public String getStreetPredirection() {
        return this.streetPredirection;
    }

    @JsonProperty("street_postdirection")
    public String getStreetPostdirection() {
        return this.streetPostdirection;
    }

    @JsonProperty("street_suffix")
    public String getStreetSuffix() {
        return this.streetSuffix;
    }

    @JsonProperty("secondary_number")
    public String getSecondaryNumber() {
        return this.secondaryNumber;
    }

    @JsonProperty("secondary_designator")
    public String getSecondaryDesignator() {
        return this.secondaryDesignator;
    }

    @JsonProperty("extra_secondary_number")
    public String getExtraSecondaryNumber() {
        return this.extraSecondaryNumber;
    }

    @JsonProperty("extra_secondary_designator")
    public String getExtraSecondaryDesignator() {
        return this.extraSecondaryDesignator;
    }

    @JsonProperty("pmb_designator")
    public String getPmbDesignator() {
        return this.pmbDesignator;
    }

    @JsonProperty("pmb_number")
    public String getPmbNumber() {
        return this.pmbNumber;
    }

    @JsonProperty("city_name")
    public String getCityName() {
        return this.cityName;
    }

    @JsonProperty("default_city_name")
    public String getDefaultCityName() {
        return this.defaultCityName;
    }

    @JsonProperty("state_abbreviation")
    public String getState() {
        return this.state;
    }

    @JsonProperty("zipcode")
    public String getZipCode() {
        return this.zipCode;
    }

    @JsonProperty("plus4_code")
    public String getPlus4Code() {
        return this.plus4Code;
    }

    @JsonProperty("delivery_point")
    public String getDeliveryPoint() {
        return this.deliveryPoint;
    }

    @JsonProperty("delivery_point_check_digit")
    public String getDeliveryPointCheckDigit() {
        return this.deliveryPointCheckDigit;
    }

    //endregion
}
