package com.smartystreets.api.us_reverse_geo;

import com.google.api.client.util.Key;

public class Coordinate {
    //region [ Fields ]

    @Key("latitude")
    private double latitude;

    @Key("longitude")
    private double longitude;

    @Key("license")
    private int license;

    @Key("zipcode")
    private String zipcode;

    //endregion

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    public int getLicense() { return this.license; }

    public String getZipCode() { return this.zipcode; }

}
