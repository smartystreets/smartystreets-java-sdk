package com.smartystreets.api.us_reverse_geo;

import com.google.api.client.util.Key;

public class Address {
    //region [ Fields ]

    @Key("street")
    private String street;

    @Key("city")
    private String city;

    @Key("state_abbreviation")
    private String stateAbbreviation;

    @Key("zip_code")
    private String zipCode;

    //endregion

    public String getStreet() { return this.street; }

    public String getCity() { return this.city; }

    public String getStateAbbreviation() { return this.stateAbbreviation; }

    public String getZipCode() { return this.zipCode; }

}
