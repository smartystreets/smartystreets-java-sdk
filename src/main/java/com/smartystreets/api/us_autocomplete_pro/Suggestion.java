package com.smartystreets.api.us_autocomplete_pro;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/us-autocomplete-pro-api#http-response"
 */
public class Suggestion implements Serializable {
    //region [ Fields ]

    private String streetLine;
    private String secondary;
    private String city;
    private String state;
    private String zipcode;
    private Integer entries;

    //region [ Fields ]

    //region [ Getters ]

    @JsonProperty("street_line")
    public String getStreetLine() {
        return streetLine;
    }

    public String getSecondary() { return secondary; }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() { return zipcode; }

    public Integer getEntries() { return entries; }

    //endregion
}
