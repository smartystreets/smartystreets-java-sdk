package com.smartystreets.api.us_zipcode;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/us-zipcode-api#zipcodes"
 */
public class AlternateCounty implements Serializable {

    //region [ Fields ]

    private String countyFips;
    private String countyName;
    private String stateAbbreviation;
    private String state;

    //endregion

    //region [ Getters ]

    @JsonProperty("county_fips")
    public String getCountyFips() {
        return countyFips;
    }

    @JsonProperty("county_name")
    public String getCountyName() {
        return countyName;
    }

    @JsonProperty("state_abbreviation")
    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    //endregion
}
