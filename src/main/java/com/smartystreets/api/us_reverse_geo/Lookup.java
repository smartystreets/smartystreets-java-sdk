package com.smartystreets.api.us_reverse_geo;

import java.io.Serializable;

public class Lookup implements Serializable {

    //region [ Fields ]

    private double latitude;
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
