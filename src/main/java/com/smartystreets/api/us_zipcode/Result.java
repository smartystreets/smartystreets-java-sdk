package com.smartystreets.api.us_zipcode;

import java.util.ArrayList;
import com.google.api.client.util.Key;

public class Result {
    @Key                private String status;
    @Key                private String reason;
    @Key("input_index") private int inputIndex;
    @Key("input_id")    private String inputId;
    @Key("city_states") CityState[] cityStates;
    @Key                ZipCode[] zipcodes;

    public Result() {
    }

    public boolean isValid() {
        return (this.status == null && this.reason == null);
    }

    public CityState getCityState(int index) {
        return this.cityStates[index];
    }

    public ZipCode getZipCode(int index) {
        return this.zipcodes[index];
    }

    /**********************************************************************************************
     * Getters
     **********************************************************************************************/

    public String getStatus() {
        return this.status;
    }

    public String getReason() {
        return this.reason;
    }

    public int getInputIndex() {
        return this.inputIndex;
    }

    public String getInputId() {
        return this.inputId;
    }

    public CityState[] getCityStates() {
        return this.cityStates;
    }

    public ZipCode[] getZipcodes() {
        return this.zipcodes;
    }
}
