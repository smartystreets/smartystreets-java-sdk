package com.smartystreets.api.us_reverse_geo;

import com.google.api.client.util.Key;

public class Result {
    //region [ Fields ]

    @Key("address")
    private Address address;

    @Key("coordinate")
    private Coordinate coordinate;

    @Key("distance")
    private double distance;

    @Key("latitude")
    private double latitude;

    @Key("longitude")
    private double longitude;

    @Key("street")
    private String street;

    @Key("city")
    private String city;

    @Key("state_abbreviation")
    private String stateAbbreviation;

    @Key("zipcode")
    private String zipcode;

    //endregion

    public Address getAddress() {
        return this.address;
    }

    public Coordinate getCoordinate() { return this.coordinate; }

    public Double getDistance() { return this.distance; }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() { return this.longitude; }

    public String getStreet() { return this.street; }

    public String getCity() { return this.city; }

    public String getStateAbbreviation() { return this.stateAbbreviation; }

    public String getZipCode() { return this.zipcode; }

}
