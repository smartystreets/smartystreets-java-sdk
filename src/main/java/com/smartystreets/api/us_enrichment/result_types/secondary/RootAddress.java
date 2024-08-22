package com.smartystreets.api.us_enrichment.result_types.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootAddress {

    private int secondaryCount;

    private String smartyKey;

    private String primaryNumber;

    private String streetPredirection;

    private String streetName;

    private String streetSuffix;

    private String streetPostdirection;

    private String cityName;

    private String stateAbbreviation;

    private String zipcode;

    private String plus4Code;

    @JsonProperty("secondary_count")
    public int getSecondaryCount() {
        return secondaryCount;
    }

    @JsonProperty("smarty_key")
    public String getSmartyKey() {
        return smartyKey;
    }

    @JsonProperty("primary_number")
    public String getPrimaryNumber() {
        return primaryNumber;
    }

    @JsonProperty("street_predirection")
    public String getStreetPredirection() {
        return streetPredirection;
    }

    @JsonProperty("street_name")
    public String getStreetName() {
        return streetName;
    }

    @JsonProperty("street_suffix")
    public String getStreetSuffix() {
        return streetSuffix;
    }

    @JsonProperty("street_postdirection")
    public String getStreetPostdirection() {
        return streetPostdirection;
    }

    @JsonProperty("city_name")
    public String getCityName() {
        return cityName;
    }

    @JsonProperty("state_abbreviation")
    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    @JsonProperty("zipcode")
    public String getZipcode() {
        return zipcode;
    }

    @JsonProperty("plus4_code")
    public String getPlus4Code() {
        return plus4Code;
    }

    @Override
    public String toString() {
        return "RootAddress{" +
                "secondary_count=" + getSecondaryCount() +
                ", smarty_key='" + getSmartyKey() + '\'' +
                ", primary_number='" + getPrimaryNumber() + '\'' +
                ", street_predirection='" + getStreetPredirection() + '\'' +
                ", street_name='" + getStreetName() + '\'' +
                ", street_suffix='" + getStreetSuffix() + '\'' +
                ", street_postdirection='" + getStreetPostdirection() + '\'' +
                ", city_name='" + getCityName() + '\'' +
                ", state_abbreviation='" + getStateAbbreviation() + '\'' +
                ", zipcode='" + getZipcode() + '\'' +
                ", plus4_code='" + getPlus4Code() + '\'' +
                '}';
    }
}

