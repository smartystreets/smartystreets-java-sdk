package com.smartystreets.api.us_zipcode;

import java.util.ArrayList;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;

public class Result {
    private boolean isValid;

    @Key                private String status;
    @Key                private String reason;
    @Key("input_index") private int inputIndex;
    @Key("input_id")    private String inputId;
    @Key("city_states") private ArrayList<CityState> cityStates;
    @Key                private ArrayList<Zipcode> zipcodes;

    public Result() {

    }

    public class CityState {
        @Key                        private String city;
        @Key("mailable_city")       private String mailableCity;
        @Key("state_abbreviation")  private String stateAbbreviation;
        @Key                        private String state;

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
    }
}
