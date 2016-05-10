package com.smartystreets.api.us_zipcode;

import com.google.api.client.util.Key;

public class CityState {
    public CityState() {
    }

    @Key                        private String city;
    @Key("mailable_city")       private boolean mailableCity;
    @Key("state_abbreviation")  private String stateAbbreviation;
    @Key                        private String state;

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
}
