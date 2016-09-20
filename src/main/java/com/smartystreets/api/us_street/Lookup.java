package com.smartystreets.api.us_street;

import com.google.api.client.util.Key;
import com.google.api.client.util.Strings;
import com.smartystreets.api.MatchType;

import java.util.ArrayList;

public class Lookup {
    //region [ Fields ]

    private ArrayList<Candidate> result;

    private String inputId;

    @Key("street")
    private String street;

    @Key("street2")
    private String street2;

    @Key("secondary")
    private String secondary;

    @Key("city")
    private String city;

    @Key("state")
    private String state;

    @Key("zipcode")
    private String zipCode;

    @Key("lastline")
    private String lastline;

    @Key("addressee")
    private String addressee;

    @Key("urbanization")
    private String urbanization;

    @Key("match")
    private String match;

    @Key("candidates")
    private int maxCandidates;

    //endregion

    //region [ Constructors ]

    public Lookup() {
        this.maxCandidates = 1;
        this.result = new ArrayList<>();
    }

    public Lookup(String freeformAddress) {
        this();
        this.street = freeformAddress;
    }

    //endregion

    //region [ Methods ]

    public void addToResult(Candidate newCandidate) {
        this.result.add(newCandidate);
    }

    //endregion

    //region [ Getters ]

    public ArrayList<Candidate> getResult() {
        return this.result;
    }

    public Candidate getResult(int index) {
        return this.result.get(index);
    }

    public String getInputId() {
        return this.inputId;
    }

    public String getStreet() {
        return this.street;
    }

    public String getStreet2() {
        return this.street2;
    }

    public String getSecondary() {
        return this.secondary;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public String getLastline() {
        return this.lastline;
    }

    public String getAddressee() {
        return this.addressee;
    }

    public String getUrbanization() {
        return this.urbanization;
    }

    public MatchType getMatch() {
        if (this.match.equals("strict") )
            return MatchType.STRICT;
        if (this.match.equals("range") )
            return MatchType.RANGE;
        if (this.match.equals("invalid") )
            return MatchType.INVALID;
        return null;
    }

    public int getMaxCandidates() {
        return this.maxCandidates;
    }

    //endregion

    //region [ Setters ]

    public void setResult(ArrayList<Candidate> result) {
        this.result = result;
    }

    public Lookup setInputId(String inputId) {
        this.inputId = inputId;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setLastline(String lastline) {
        this.lastline = lastline;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public void setUrbanization(String urbanization) {
        this.urbanization = urbanization;
    }

    public void setMatch(MatchType match) {
        this.match = match.getName();
    }

    public void setMaxCandidates(int maxCandidates) throws IllegalArgumentException {
        if (maxCandidates > 0) {
            this.maxCandidates = maxCandidates;
        } else {
            throw new IllegalArgumentException("Max candidates must be a positive integer.");
        }
    }

    //endregion
}


