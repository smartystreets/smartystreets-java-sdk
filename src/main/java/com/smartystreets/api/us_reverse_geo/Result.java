package com.smartystreets.api.us_reverse_geo;

import java.io.Serializable;

public class Result implements Serializable {
    //region [ Fields ]

    private Address address;
    private Coordinate coordinate;
    private double distance;

    //endregion

    public Address getAddress() {
        return this.address;
    }

    public Coordinate getCoordinate() { return this.coordinate; }

    public Double getDistance() { return this.distance; }

}
