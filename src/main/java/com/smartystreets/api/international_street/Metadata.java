package com.smartystreets.api.international_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/international-street-api#metadata"
 */
public class
Metadata implements Serializable {
    //region [ Fields ]

    private double latitude;
    private double longitude;
    private String geocodePrecision;
    private String maxGeocodePrecision;
    private String addressFormat;

    //endregion

    // [ Getters ]

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @JsonProperty("geocode_precision")
    public String getGeocodePrecision() {
        return geocodePrecision;
    }

    @JsonProperty("max_geocode_precision")
    public String getMaxGeocodePrecision() {
        return maxGeocodePrecision;
    }

    @JsonProperty("address_format")
    public String getAddressFormat() {
        return addressFormat;
    }

    //endregion
}
