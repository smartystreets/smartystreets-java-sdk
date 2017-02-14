package com.smartystreets.api.us_street;

import com.google.api.client.util.Key;

/**
 * This class contains the matched address broken down into its<br>
 *     fundamental pieces.
 * @see "https://smartystreets.com/docs/cloud/us-street-api#components"
 */
public class Components {

    //region [ Fields ]

    @Key("urbanization")
    private String urbanization;

    @Key("primary_number")
    private String primaryNumber;

    @Key("street_name")
    private String streetName;

    @Key("street_predirection")
    private String streetPredirection;

    @Key("street_postdirection")
    private String streetPostdirection;

    @Key("street_suffix")
    private String streetSuffix;

    @Key("secondary_number")
    private String secondaryNumber;

    @Key("secondary_designator")
    private String secondaryDesignator;

    @Key("extra_secondary_number")
    private String extraSecondaryNumber;

    @Key("extra_secondary_designator")
    private String extraSecondaryDesignator;

    @Key("pmb_designator")
    private String pmbDesignator;

    @Key("pmb_number")
    private String pmbNumber;

    @Key("city_name")
    private String cityName;

    @Key("default_city_name")
    private String defaultCityName;

    @Key("state_abbreviation")
    private String state;

    @Key("zipcode")
    private String zipCode;

    @Key("plus4_code")
    private String plus4Code;

    @Key("delivery_point")
    private String deliveryPoint;

    @Key("delivery_point_check_digit")
    private String deliveryPointCheckDigit;

    //endregion

    //region [ Getters ]

    public String getUrbanization() {
        return this.urbanization;
    }

    public String getPrimaryNumber() {
        return this.primaryNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public String getStreetPredirection() {
        return this.streetPredirection;
    }

    public String getStreetPostdirection() {
        return this.streetPostdirection;
    }

    public String getStreetSuffix() {
        return this.streetSuffix;
    }

    public String getSecondaryNumber() {
        return this.secondaryNumber;
    }

    public String getSecondaryDesignator() {
        return this.secondaryDesignator;
    }

    public String getExtraSecondaryNumber() {
        return this.extraSecondaryNumber;
    }

    public String getExtraSecondaryDesignator() {
        return this.extraSecondaryDesignator;
    }

    public String getPmbDesignator() {
        return this.pmbDesignator;
    }

    public String getPmbNumber() {
        return this.pmbNumber;
    }

    public String getCityName() {
        return this.cityName;
    }

    public String getDefaultCityName() {
        return this.defaultCityName;
    }

    public String getState() {
        return this.state;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public String getPlus4Code() {
        return this.plus4Code;
    }

    public String getDeliveryPoint() {
        return this.deliveryPoint;
    }

    public String getDeliveryPointCheckDigit() {
        return this.deliveryPointCheckDigit;
    }

    //endregion
}
