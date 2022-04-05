package com.smartystreets.api.us_zipcode;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/us-zipcode-api#root"
 */
public class Result implements Serializable {
    //region [ Fields ]

    String status;
    String reason;
    private String inputId;
    private int inputIndex;
    private City[] cities;
    private ZipCode[] zipCodes;

    //endregion

    public Result() {
    }

    public boolean isValid() {
        return (this.status == null && this.reason == null);
    }

    public City getCity(int index) {
        return this.cities[index];
    }

    public ZipCode getZipCode(int index) {
        return this.zipCodes[index];
    }

    //region [ Getters ]

    /**
     *
     * @return Returns a status if there was no match
     */

    @JsonProperty("input_id")
    public String getInputId() { return this.inputId; }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("reason")
    public String getReason() {
        return this.reason;
    }

    @JsonProperty("input_index")
    public int getInputIndex() {
        return this.inputIndex;
    }

    @JsonProperty("city_states")
    public City[] getCities() {
        return this.cities;
    }

    @JsonProperty("zipcodes")
    public ZipCode[] getZipCodes() {
        return this.zipCodes;
    }

    //endregion
}
