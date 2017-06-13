package com.smartystreets.api.international_street;

import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/international-street-api#metadata"
 */
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

    @Key("address_format")
    private String addressFormat;

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

    public String getAddressFormat() {
        return addressFormat;
    }

    //endregion
}
