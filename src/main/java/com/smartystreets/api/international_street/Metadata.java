package com.smartystreets.api.international_street;

import com.google.api.client.util.Key;

public class Metadata {
    //region [ Fields ]

    @Key("latitude")
    private double latitude;

    @Key("longitude")
    private double longitude;

    @Key("geocode_precision")
    private String geocodePrecision;

    @Key("max_geocode_precision")
    private String maxGeocodePrecision;

    //endregion

    // [ Getters ]

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getGeocodePrecision() {
        return geocodePrecision;
    }

    public String getMaxGeocodePrecision() {
        return maxGeocodePrecision;
    }

    //endregion
}
