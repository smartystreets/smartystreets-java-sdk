package com.smartystreets.api.us_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In addition to holding the input data for this lookup, this class<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/us-street-api#input-fields"
 */
public class Lookup implements Serializable {
    //region [ Fields ]

    private List<Candidate> result;
    private String inputId;
    private String street;
    private String street2;
    private String secondary;
    private String city;
    private String state;
    private String zipCode;
    private String lastline;
    private String addressee;
    private String urbanization;
    private String countySource;
    private String match;
    private String format;
    private int candidates;

    //This is a temporary flag meant to fix an intermittent data issue
    //Unless explicitly instructed by the Smarty Tech Support team, DO NOT use this parameter
    private String compatibility;

    private Map<String, String> customParamMap;

    //endregion

    //region [ Constructors ]

    public Lookup() {
        this.candidates = 1;
        this.result = new ArrayList<>();
        this.customParamMap = new HashMap<>();
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

    @JsonProperty("result")
    public List<Candidate> getResult() {
        return this.result;
    }

    public Candidate getResult(int index) {
        return this.result.get(index);
    }

    @JsonProperty("input_id")
    public String getInputId() {
        return this.inputId;
    }

    @JsonProperty("street")
    public String getStreet() {
        return this.street;
    }

    @JsonProperty("street2")
    public String getStreet2() {
        return this.street2;
    }

    @JsonProperty("secondary")
    public String getSecondary() {
        return this.secondary;
    }

    @JsonProperty("city")
    public String getCity() {
        return this.city;
    }

    @JsonProperty("state")
    public String getState() {
        return this.state;
    }

    @JsonProperty("zipcode")
    public String getZipCode() {
        return this.zipCode;
    }

    @JsonProperty("lastline")
    public String getLastline() {
        return this.lastline;
    }

    @JsonProperty("addressee")
    public String getAddressee() {
        return this.addressee;
    }

    @JsonProperty("urbanization")
    public String getUrbanization() {
        return this.urbanization;
    }

    @JsonProperty("county_source")
    public String getCountySource() {
        if (this.countySource == null) {
            return null;
        }
        if (this.countySource.equals("postal") ) {
            return "postal";
        }
        if (this.countySource.equals("geographic") ) {
            return "geographic";
        }
        return this.countySource;
    }

    @JsonProperty("match")
    public String getMatch() {
        if (this.match == null)
            return null;
        if (this.match.equals("strict") )
            return "strict";
        if (this.match.equals("invalid") )
            return "invalid";
        if (this.match.equals("enhanced") )
            return "enhanced";
        return null;
    }

    /**
     * This is a temporary flag meant to fix an intermittent data issue
     * Unless explicitly instructed by the Smarty Tech Support team, DO NOT use this parameter
     *
     * @deprecated - Temporary - will be removed in a future release.
     */
    @JsonProperty("compatibility")
    public String getCompatibility() {
        return this.compatibility;
    }

    @JsonProperty("format")
    public String getFormat(){
        if (this.format == null)
            return null;
        if (this.format.equals("default"))
            return "default";
        if (this.format.equals("project-usa"))
            return "project-usa";
        return null;
    }

    @JsonProperty("candidates")
    public int getMaxCandidates() {
        return this.candidates;
    }

    @JsonProperty("custom_param_map")
    public Map<String, String> getCustomParamMap() {
        return this.customParamMap;
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

    public void setCountySource(CountySource countySource) {
        this.countySource = countySource.getName();
    }

    /**
     * This is a temporary flag meant to fix an intermittent data issue
     * Unless explicitly instructed by the Smarty Tech Support team, DO NOT use this parameter
     *
     * @deprecated - Temporary - will be removed in a future release.
     */
    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
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
     * Sets the format output for this lookup <br>
     * @param format The format output
     */
    public void setFormat(OutputFormat format) {
        this.format = format.getName();
    }

    /**
     * Sets the maximum number of valid addresses returned when the input is ambiguous.
     * @param candidates Defaults to 1. Must be an integer between 1 and 10, inclusive.
     * @throws IllegalArgumentException
     */
    public void setMaxCandidates(int candidates) throws IllegalArgumentException {
        if (candidates > 0) {
            this.candidates = candidates;
        } else {
            throw new IllegalArgumentException("Max candidates must be a positive integer.");
        }
    }

    public void addCustomParameter(String parameter, String value) {
        this.customParamMap.put(parameter, value);
    }

    //endregion
}


