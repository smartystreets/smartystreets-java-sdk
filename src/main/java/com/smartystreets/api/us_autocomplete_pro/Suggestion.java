package com.smartystreets.api.us_autocomplete_pro;

import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#http-response"
 */
public class Suggestion {
    //region [ Fields ]

    @Key("street_line")
    private String streetLine;

    @Key("secondary")
    private String secondary;

    @Key("city")
    private String city;

    @Key("state")
    private String state;

    @Key("zipcode")
    private String zipcode;

    @Key("entries")
    private Integer entries;

    //region [ Fields ]

    //region [ Getters ]

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
