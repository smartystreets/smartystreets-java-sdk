package com.smartystreets.api.us_autocomplete;

import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#http-response"
 */
public class Suggestion {
    //region [ Fields ]

    @Key("text")
    private String text;

    @Key("street_line")
    private String streetLine;

    @Key("city")
    private String city;

    @Key("state")
    private String state;

    //region [ Fields ]

    //region [ Getters ]

    public String getText() {
        return text;
    }

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
