package com.smartystreets.api.us_zipcode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/us-zipcode-api#http-request-input-fields"
 */
public class Lookup implements Serializable {
    //region [ Fields ]

    //private static final long serialVersionUID = 1L;
    private Result result;
    private String inputId;
    private String city;
    private String state;
    private String zipcode;

    //This is a temporary flag meant to fix an intermittent data issue
    //Unless explicitly instructed by the Smarty Tech Support team, DO NOT use this parameter
    private String compatibility;

    private Map<String, String> customParamMap;

    //endregion

    public Lookup() {
        this.result = new Result();
        this.customParamMap = new HashMap<>();
    }

    public Lookup(String zipcode) {
        this();
        this.zipcode = zipcode;
    }

    public Lookup(String city, String state) {
        this();
        this.city = city;
        this.state = state;
    }

    public Lookup(String city, String state, String zipcode) {
        this();
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    //region [ Getters ]

    @JsonIgnore
    public Result getResult() {
        return this.result;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getZipCode() {
        return this.zipcode;
    }

    public String getInputId() {
        return this.inputId;
    }

    /**
     * This is a temporary flag meant to fix an intermittent data issue
     * Unless explicitly instructed by the Smarty Tech Support team, DO NOT use this parameter
     *
     * @deprecated - Temporary - will be removed in a future release.
     */
    public String getCompatibility() {
        return this.compatibility;
    }

    @JsonProperty("custom_param_map")
    public Map<String, String> getCustomParamMap() {
        return this.customParamMap;
    }

    //endregion

    //region [ Setters ]

    @JsonProperty("result")
    public void setResult(Result result) {
        this.result = result;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("zipcode")
    public void setZipCode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * This is a temporary flag meant to fix an intermittent data issue
     * Unless explicitly instructed by the Smarty Tech Support team, DO NOT use this parameter
     *
     * * @deprecated - Temporary - will be removed in a future release.
     */
    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public Lookup setInputId(String inputId) {
        this.inputId = inputId;
        return this;
    }

    public void addCustomParameter(String parameter, String value) {
        this.customParamMap.put(parameter, value);
    }

    //endregion
}
