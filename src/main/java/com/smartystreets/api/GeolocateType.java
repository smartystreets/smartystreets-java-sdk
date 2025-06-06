package com.smartystreets.api;

/**
 * This field corresponds to the <b>geolocate</b> and <b>geolocate_precision</b> fields in the US Autocomplete API.
 *
 * @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#http-request-input-fields"
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
