package com.smartystreets.api.us_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/us-street-api#metadata"
 */
public class Metadata implements Serializable {
    //region [ Fields ]

    private String recordType;
    private String zipType;
    private String countyFips;
    private String countyName;
    private String carrierRoute;
    private String congressionalDistrict;
    private String buildingDefaultIndicator;
    private String rdi;
    private String elotSequence;
    private String elotSort;
    private double latitude;
    private double longitude;
    private int coordinateLicense;
    private String precision;
    private String timeZone;
    private double utcOffset;
    private boolean obeysDst;
    private boolean ewsMatch;

    //endregion

    //region [ Getters ]

    @JsonProperty("record_type")
    public String getRecordType() {
        return this.recordType;
    }

    @JsonProperty("zip_type")
    public String getZipType() {
        return this.zipType;
    }

    @JsonProperty("county_fips")
    public String getCountyFips() {
        return this.countyFips;
    }

    @JsonProperty("county_name")
    public String getCountyName() {
        return this.countyName;
    }

    @JsonProperty("carrier_route")
    public String getCarrierRoute() {
        return this.carrierRoute;
    }

    @JsonProperty("congressional_district")
    public String getCongressionalDistrict() {
        return this.congressionalDistrict;
    }

    @JsonProperty("building_default_indicator")
    public String getBuildingDefaultIndicator() {
        return this.buildingDefaultIndicator;
    }

    @JsonProperty("rdi")
    public String getRdi() {
        return this.rdi;
    }

    @JsonProperty("elot_sequence")
    public String getElotSequence() {
        return this.elotSequence;
    }

    @JsonProperty("elot_sort")
    public String getElotSort() {
        return this.elotSort;
    }

    @JsonProperty("latitude")
    public double getLatitude() {
        return this.latitude;
    }

    @JsonProperty("longitude")
    public double getLongitude() {
        return this.longitude;
    }

    @JsonProperty("coordinate_license")
    public int getCoordinateLicense() { return this.coordinateLicense; }

    @JsonProperty("precision")
    public String getPrecision() {
        return this.precision;
    }

    @JsonProperty("time_zone")
    public String getTimeZone() {
        return this.timeZone;
    }

    // TODO: Coordinate Licensei

    @JsonProperty("utc_offset")
    public double getUtcOffset() {
        return this.utcOffset;
    }

    @JsonProperty("dst")
    public boolean obeysDst() {
        return this.obeysDst;
    }

    @JsonProperty("ews_match")
    public boolean isEwsMatch() {
        return this.ewsMatch;
    }


    //endregion
}
