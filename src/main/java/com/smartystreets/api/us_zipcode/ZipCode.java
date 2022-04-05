package com.smartystreets.api.us_zipcode;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/us-zipcode-api#zipcodes"
 */
public class ZipCode implements Serializable {
    //region [ fields ]

    private String zipCode;
    private String zipCodeType;
    private String defaultCity;
    private String countyFips;
    private String countyName;
    private String stateAbbreviation;
    private String state;
    private double latitude;
    private double longitude;
    private String precision;
    private AlternateCounty[] alternateCounties;

    //endregion

    //region [ Getter ]

    @JsonProperty("zipcode")
    public String getZipCode() {
        return this.zipCode;
    }

    @JsonProperty("zipcode_type")
    public String getZipCodeType() {
        return this.zipCodeType;
    }

    @JsonProperty("default_city")
    public String getDefaultCity() {
        return this.defaultCity;
    }

    @JsonProperty("county_fips")
    public String getCountyFips() {
        return this.countyFips;
    }

    @JsonProperty("county_name")
    public String getCountyName() {
        return this.countyName;
    }

    @JsonProperty("state_abbreviation")
    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("latitude")
    public double getLatitude() {
        return this.latitude;
    }

    @JsonProperty("longitude")
    public double getLongitude() {
        return this.longitude;
    }

    @JsonProperty("precision")
    public String getPrecision() {
        return this.precision;
    }

    @JsonProperty("alternate_counties")
    public AlternateCounty[] getAlternateCounties() {
        return alternateCounties;
    }

    @JsonProperty("alternate_county")
    public AlternateCounty getAlternateCounty(int index) {
        return alternateCounties[index];
    }

    //endregion
}
