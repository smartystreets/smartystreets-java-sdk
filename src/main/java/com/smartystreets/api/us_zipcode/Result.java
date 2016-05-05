package com.smartystreets.api.us_zipcode;

import javax.json.JsonObject;
import java.util.ArrayList;

public class Result {

    private boolean isValid;
    private String status;
    private String reason;

    private String inputIndex;
    private String inputId;
    private ArrayList<CityState> cityStates;
    private ArrayList<Zipcode> zipcodes;

    public Result(/*JsonObject obj*/) {

    }

    public class CityState {
        private String city;
        private String mailableCity;
        private String stateAbbreviation;
        private String state;

    }

    public class Zipcode {
        private String zipcode;
        private String zipcodeType;
        private String defaultCity;
        private String countyFips;
        private String countyName;
        private String latitude;
        private String longitude;
        private String precision;
    }
}
