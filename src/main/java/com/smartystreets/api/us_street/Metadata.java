package com.smartystreets.api.us_street;

import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/us-street-api#metadata"
 */
public class Metadata {
    //region [ Fields ]

    @Key("record_type")
    private String recordType;

    @Key("zip_type")
    private String zipType;

    @Key("county_fips")
    private String countyFips;

    @Key("county_name")
    private String countyName;

    @Key("carrier_route")
    private String carrierRoute;

    @Key("congressional_district")
    private String congressionalDistrict;

    @Key("building_default_indicator")
    private String buildingDefaultIndicator;

    @Key("rdi")
    private String rdi;

    @Key("elot_sequence")
    private String elotSequence;

    @Key("elot_sort")
    private String elotSort;

    @Key("latitude")
    private double latitude;

    @Key("longitude")
    private double longitude;

    @Key("precision")
    private String precision;

    @Key("time_zone")
    private String timeZone;

    @Key("utc_offset")
    private double utcOffset;

    @Key("dst")
    private boolean obeysDst;

    @Key("ews_match")
    private boolean isEwsMatch;

    //endregion

    //region [ Getters ]

    public String getRecordType() {
        return this.recordType;
    }

    public String getZipType() {
        return this.zipType;
    }

    public String getCountyFips() {
        return this.countyFips;
    }

    public String getCountyName() {
        return this.countyName;
    }

    public String getCarrierRoute() {
        return this.carrierRoute;
    }

    public String getCongressionalDistrict() {
        return this.congressionalDistrict;
    }

    public String getBuildingDefaultIndicator() {
        return this.buildingDefaultIndicator;
    }

    public String getRdi() {
        return this.rdi;
    }

    public String getElotSequence() {
        return this.elotSequence;
    }

    public String getElotSort() {
        return this.elotSort;
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

    public String getTimeZone() {
        return this.timeZone;
    }

    public double getUtcOffset() {
        return this.utcOffset;
    }

    public boolean obeysDst() {
        return this.obeysDst;
    }

    public boolean isEwsMatch() {
        return this.isEwsMatch;
    }


    //endregion
}
