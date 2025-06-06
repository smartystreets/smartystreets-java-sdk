package com.smartystreets.api;

/**
 * This field corresponds to the <b>geolocate_precision</b> field in the US Autocomplete Pro API.
 *
 * @see "https://smartystreets.com/docs/cloud/us-autocomplete-pro-api#http-request-input-fields"
 */
public enum GeolocateType {
    CITY("city"), NONE("none");

    private final String name;

    GeolocateType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
