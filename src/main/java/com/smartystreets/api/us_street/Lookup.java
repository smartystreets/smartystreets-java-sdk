package com.smartystreets.api.us_street;

import com.google.api.client.util.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/us-street-api#input-fields"
 */
public class Lookup {
    //region [ Fields ]

    private List<Candidate> result;

    @Key("input_id")
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

    /**
     * This constructor accepts a freeform address. That means the whole address is in one string.
     * @param freeformAddress
     */
    public Lookup(String freeformAddress) {
        this();
        this.street = freeformAddress;
    }

    //endregion

    //region [ Methods ]

    void addToResult(Candidate newCandidate) {
        this.result.add(newCandidate);
    }

    //endregion

    //region [ Getters ]

    public List<Candidate> getResult() {
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
        if (this.match == null)
            return null;
        if (this.match.equals("strict") )
            return MatchType.STRICT;
        if (this.match.equals("range") )
            return MatchType.RANGE;
        if (this.match.equals("invalid") )
            return MatchType.INVALID;
        if (this.match.equals("enhanced") )
            return MatchType.ENHANCED;
        return null;
    }

    public String getMatchString() {
        MatchType match = getMatch();
        if (match != null)
            return match.getName();
        return null;
    }

    public int getMaxCandidates() {
        return this.maxCandidates;
    }

    //endregion

    //region [ Setters ]

    public void setResult(List<Candidate> result) {
        this.result = result;
    }

    public Lookup setInputId(String inputId) {
        this.inputId = inputId;
        return this;
    }

    /**
     * You can optionally put the entire address in the <b>street</b> field,<br>
     *     and leave the other fields blank. We call this a <b>freeform address</b>.<br>
     *     <i><b>Note:</b> Freeform addresses are slightly less reliable.</i>
     *
     *     @param street If using a freeform address, do <b>not</b> include country information
     */
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

    /**
     * Sets the match output strategy to be employed for this lookup.<br>
     *
     * @see "https://smartystreets.com/docs/cloud/us-street-api#input-fields"
     * @param match The match output strategy
     */
    public void setMatch(MatchType match) {
        this.match = match.getName();
    }

    /**
     * Sets the maximum number of valid addresses returned when the input is ambiguous.
     * @param maxCandidates Defaults to 1. Must be an integer between 1 and 10, inclusive.
     * @throws IllegalArgumentException
     */
    public void setMaxCandidates(int maxCandidates) throws IllegalArgumentException {
        if (maxCandidates > 0) {
            this.maxCandidates = maxCandidates;
        } else {
            throw new IllegalArgumentException("Max candidates must be a positive integer.");
        }
    }

    //endregion
}


