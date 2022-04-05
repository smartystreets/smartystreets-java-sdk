package com.smartystreets.api.us_extract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see <a href="https://smartystreets.com/docs/cloud/us-extract-api#http-response-status">SmartyStreets US Extract API docs</a>
 */
public class Metadata implements Serializable {
    //region [ Fields ]

    private int lines;
    private boolean unicode;
    private int addressCount;
    private int verifiedCount;
    private int bytes;
    private int characterCount;

    //endregion

    //region [ Getters ]

    @JsonProperty("lines")
    public int getLines() {
        return lines;
    }

    @JsonProperty("unicode")
    public boolean isUnicode() {
        return unicode;
    }

    @JsonProperty("address_count")
    public int getAddressCount() {
        return addressCount;
    }

    @JsonProperty("verified_count")
    public int getVerifiedCount() {
        return verifiedCount;
    }

    @JsonProperty("bytes")
    public int getBytes() {
        return bytes;
    }

    @JsonProperty("character_count")
    public int getCharacterCount() {
        return characterCount;
    }

    //endregion
}
