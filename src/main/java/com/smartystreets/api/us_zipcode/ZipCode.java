package com.smartystreets.api.us_zipcode;

import com.google.api.client.util.Key;

public class ZipCode {
    @Key
    private String zipcode;
    @Key("zipcode_type")    private String zipcodeType;
    @Key("default_city")    private String defaultCity;
    @Key("county_fips")     private String countyFips;
    @Key("county_name")     private String countyName;
    @Key                    private double latitude;
    @Key                    private double longitude;
    @Key                    private String precision;

    public String getZipcode() {
        return this.zipcode;
    }

    public String getZipcodeType() {
        return this.zipcodeType;
    }

    public String getDefaultCity() {
        return this.defaultCity;
    }

    public String getCountyFips() {
        return this.countyFips;
    }

    public String getCountyName() {
        return this.countyName;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getPrecision() {
        return this.precision;
    }
}
