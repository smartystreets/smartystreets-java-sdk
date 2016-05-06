package com.smartystreets.api.us_zipcode;

import java.util.ArrayList;
import com.google.api.client.util.Key;

public class Result {
    @Key                private String status;
    @Key                private String reason;
    @Key("input_index") private int inputIndex;
    @Key("input_id")    private String inputId;
    @Key("city_states") private ArrayList<CityState> cityStates;
    @Key                private ArrayList<Zipcode> zipcodes;

    public Result() {
        this.cityStates = new ArrayList<>();
        this.zipcodes = new ArrayList<>();
    }

    public boolean isValid() {
        return (this.status == null && this.reason == null);
    }

    public CityState getCityState(int index) {
        return this.cityStates.get(index);
    }

    public Zipcode getZipCode(int index) {
        return this.zipcodes.get(index);
    }

    public class CityState {
        @Key                        private String city;
        @Key("mailable_city")       private String mailableCity;
        @Key("state_abbreviation")  private String stateAbbreviation;
        @Key                        private String state;


        public String getCity() {
            return this.city;
        }

        public String getMailableCity() {
            return this.mailableCity;
        }

        public String getStateAbbreviation() {
            return this.stateAbbreviation;
        }

        public String getState() {
            return this.state;
        }
    }

    public class Zipcode {
        @Key                    private String zipcode;
        @Key("zipcode_type")    private String zipcodeType;
        @Key("default_city")    private String defaultCity;
        @Key("county_fips")     private String countyFips;
        @Key("county_name")     private String countyName;
        @Key                    private String latitude;
        @Key                    private String longitude;
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

        public String getLatitude() {
            return this.latitude;
        }

        public String getLongitude() {
            return this.longitude;
        }

        public String getPrecision() {
            return this.precision;
        }
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

    public ArrayList<CityState> getCityStates() {
        return this.cityStates;
    }

    public ArrayList<Zipcode> getZipcodes() {
        return this.zipcodes;
    }
}
