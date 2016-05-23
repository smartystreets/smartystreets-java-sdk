package com.smartystreets.api.us_zipcode;

import com.google.api.client.util.Key;

public class ZipCode {
    //region [ fields ]

    @Key("zipcode")
    private String zipCode;

    @Key("zipcode_type")
    private String zipCodeType;

    @Key("default_city")
    private String defaultCity;

    @Key("county_fips")
    private String countyFips;

    @Key("county_name")
    private String countyName;

    @Key("latitude")
    private double latitude;

    @Key("longitude")
    private double longitude;

    @Key("precision")
    private String precision;

    //endregion

    //region [ Getter ]

    public String getZipCode() {
        return this.zipCode;
    }

    public String getZipCodeType() {
        return this.zipCodeType;
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

    //endregion
}
