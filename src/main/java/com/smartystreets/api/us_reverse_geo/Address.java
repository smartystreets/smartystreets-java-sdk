package com.smartystreets.api.us_reverse_geo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Address implements Serializable {
    //region [ Fields ]

    private String street;
    private String city;
    private String stateAbbreviation;
    private String zipCode;
    private String source;

    //endregion

    public String getStreet() { return this.street; }

    public String getCity() { return this.city; }

    @JsonProperty("state_abbreviation")
    public String getStateAbbreviation() { return this.stateAbbreviation; }

    @JsonProperty("zipcode")
    public String getZipCode() { return this.zipCode; }

    @JsonProperty("source")
    public String getSource() { return this.source; }

}
