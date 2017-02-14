package com.smartystreets.api.us_zipcode;


import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/us-zipcode-api#zipcodes"
 */
public class AlternateCounty {

    //region [ Fields ]

    @Key("county_fips")
    private String countyFips;

    @Key("county_name")
    private String countyName;

    @Key("state_abbreviation")
    private String stateAbbreviation;

    @Key("state")
    private String state;

    //endregion

    //region [ Getters ]

    public String getCountyFips() {
        return countyFips;
    }

    public String getCountyName() {
        return countyName;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public String getState() {
        return state;
    }

    //endregion
}
