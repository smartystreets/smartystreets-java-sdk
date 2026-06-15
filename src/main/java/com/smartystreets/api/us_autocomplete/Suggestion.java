package com.smartystreets.api.us_autocomplete;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://www.smarty.com/docs/apis/us-autocomplete-v2/reference#http-response-status"
 */
public class Suggestion implements Serializable {
    //region [ Fields ]

    private String smartyKey;
    private String entryId;
    private String streetLine;
    private String secondary;
    private String city;
    private String state;
    private String zipcode;
    private Integer entries;

    //endregion

    //region [ Getters ]

    @JsonProperty("smarty_key")
    public String getSmartyKey() {
        return smartyKey;
    }

    @JsonProperty("entry_id")
    public String getEntryId() {
        return entryId;
    }

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
