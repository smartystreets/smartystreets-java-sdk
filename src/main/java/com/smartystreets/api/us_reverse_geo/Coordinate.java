package com.smartystreets.api.us_reverse_geo;

import java.io.Serializable;

public class Coordinate implements Serializable {
    //region [ Fields ]

    private double latitude;
    private double longitude;
    private int license;
    private String zipcode;
    private String accuracy;

    //endregion

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getLicense() {
        switch (this.license) {
            case 1:
                return "SmartyStreets Proprietary";
            default:
                return "SmartyStreets";

        }
    }

    public String getZipCode() {
        return this.zipcode;
    }

    public String getAccuracy() {
        return this.accuracy;
    }
}
