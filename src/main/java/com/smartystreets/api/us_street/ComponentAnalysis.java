package com.smartystreets.api.us_street;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComponentAnalysis implements Serializable {

    private MatchInfo primaryNumber;

    private MatchInfo streetPredirection;

    private MatchInfo streetName;

    private MatchInfo streetPostdirection;

    private MatchInfo streetSuffix;

    private MatchInfo secondaryNumber;

    private MatchInfo secondaryDesignator;

    private MatchInfo extraSecondaryNumber;

    private MatchInfo extraSecondaryDesignator;

    private MatchInfo cityName;

    private MatchInfo stateAbbreviation;

    private MatchInfo zipCode;

    private MatchInfo plus4Code;

    private MatchInfo urbanization;

    @JsonProperty("primary_number")
    public MatchInfo getPrimaryNumber() {
        return primaryNumber;
    }

    @JsonProperty("street_predirection")
    public MatchInfo getStreetPredirection() {
        return streetPredirection;
    }

    @JsonProperty("street_name")
    public MatchInfo getStreetName() {
        return streetName;
    }

    @JsonProperty("street_postdirection")
    public MatchInfo getStreetPostdirection() {
        return streetPostdirection;
    }

    @JsonProperty("street_suffix")
    public MatchInfo getStreetSuffix() {
        return streetSuffix;
    }

    @JsonProperty("secondary_number")
    public MatchInfo getSecondaryNumber() {
        return secondaryNumber;
    }

    @JsonProperty("secondary_designator")
    public MatchInfo getSecondaryDesignator() {
        return secondaryDesignator;
    }

    @JsonProperty("extra_secondary_number")
    public MatchInfo getExtraSecondaryNumber() {
        return extraSecondaryNumber;
    }

    @JsonProperty("extra_secondary_designator")
    public MatchInfo getExtraSecondaryDesignator() {
        return extraSecondaryDesignator;
    }

    @JsonProperty("city_name")
    public MatchInfo getCityName() {
        return cityName;
    }

    @JsonProperty("state_abbreviation")
    public MatchInfo getStateAbbreviation() {
        return stateAbbreviation;
    }

    @JsonProperty("zipcode")
    public MatchInfo getZipCode() {
        return zipCode;
    }
    
    @JsonProperty("plus4_code")
    public MatchInfo getPlus4Code() {
        return plus4Code;
    }

    @JsonProperty("urbanization")
    public MatchInfo getUrbanization() {
        return urbanization;
    }

    
}

