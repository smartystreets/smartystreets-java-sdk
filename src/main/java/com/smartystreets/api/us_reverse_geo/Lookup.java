package com.smartystreets.api.us_reverse_geo;

import com.google.api.client.util.Key;

public class Lookup {
//region [ Fields ]

    @Key("latitude")
    private double latitude;

    @Key("longitude")
    private double longitude;

    private SmartyResponse response;

    //endregion

    public Lookup() { this.response = new SmartyResponse(); }

    public Lookup(double latitude, double longitude) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() { return this.latitude; }

    public Double getLongitude() { return this.longitude; }

    public SmartyResponse getResponse() { return this.response; }

    public void setResponse(SmartyResponse response) {
        this.response = response;
    }
}
