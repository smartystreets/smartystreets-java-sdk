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

    //endregion

    public Address getAddress() {
        return this.address;
    }

    public Coordinate getCoordinate() { return this.coordinate; }

    public Double getDistance() { return this.distance; }

}
