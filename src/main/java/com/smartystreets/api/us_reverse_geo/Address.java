package com.smartystreets.api.us_reverse_geo;

import java.io.Serializable;

public class Address implements Serializable {
    //region [ Fields ]

    private String street;
    private String city;
    private String stateAbbreviation;
    private String zipCode;

    //endregion

    public String getStreet() { return this.street; }

    public String getCity() { return this.city; }

    public String getStateAbbreviation() { return this.stateAbbreviation; }

    public String getZipCode() { return this.zipCode; }

}
