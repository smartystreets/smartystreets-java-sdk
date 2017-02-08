package com.smartystreets.api.us_extract;


import com.google.api.client.util.Key;

public class Metadata {
    //region [ Fields ]

    @Key("lines")
    private int lines;

    @Key("unicode")
    private boolean unicode;

    @Key("address_count")
    private int addressCount;

    @Key("verified_count")
    private int verifiedCount;

    @Key("bytes")
    private int bytes;

    @Key("character_count")
    private int characterCount;

    //endregion

    //region [ Getters ]

    public int getLines() {
        return lines;
    }

    public boolean isUnicode() {
        return unicode;
    }

    public int getAddressCount() {
        return addressCount;
    }

    public int getVerifiedCount() {
        return verifiedCount;
    }

    public int getBytes() {
        return bytes;
    }

    public int getCharacterCount() {
        return characterCount;
    }

    //endregion
}
