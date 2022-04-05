package com.smartystreets.api.us_autocomplete;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#http-response"
 */
public class Suggestion implements Serializable {
    //region [ Fields ]

    private String text;
    private String streetLine;
    private String city;
    private String state;

    //region [ Fields ]

    //region [ Getters ]

    public String getText() {
        return text;
    }

    @JsonProperty("street_line")
    public String getStreetLine() {
        return streetLine;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    //endregion
}
