package com.smartystreets.api.us_zipcode;

import com.google.api.client.util.Key;

public class City {
    //region [ Fields ]

    @Key("city")
    private String city;

    @Key("mailable_city")
    private boolean mailableCity;

    @Key("state_abbreviation")
    private String stateAbbreviation;

    @Key("state")
    private String state;

    //endregion

    //region [ Getters ]

    public String getCity() {
        return this.city;
    }

    public boolean getMailableCity() {
        return this.mailableCity;
    }

    public String getStateAbbreviation() {
        return this.stateAbbreviation;
    }

    public String getState() {
        return this.state;
    }

    //endregion
}
