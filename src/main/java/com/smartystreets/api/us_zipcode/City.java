package com.smartystreets.api.us_zipcode;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Known in the SmartyStreets US ZIP Code API documentation as a <b>city_state</b>
 * @see "https://smartystreets.com/docs/cloud/us-zipcode-api#cities"
 */
public class City implements Serializable {
    //region [ Fields ]

    private String city;
    private boolean mailableCity;
    private String stateAbbreviation;
    private String state;

    //endregion

    //region [ Getters ]

    @JsonProperty("city")
    public String getCity() {
        return this.city;
    }

    @JsonProperty("mailable_city")
    public boolean getMailableCity() {
        return this.mailableCity;
    }

    @JsonProperty("state_abbreviation")
    public String getStateAbbreviation() {
        return this.stateAbbreviation;
    }

    @JsonProperty("state")
    public String getState() {
        return this.state;
    }

    //endregion
}
