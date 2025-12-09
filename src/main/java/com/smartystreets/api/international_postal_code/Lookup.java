package com.smartystreets.api.international_postal_code;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Lookup input for International Postal Code API (see Go Lookup struct/docs)
 * Holds input data and the resulting candidates.
 * @see "https://smartystreets.com/docs/cloud/international-postal-code-api"
 */
public class Lookup {
    @JsonProperty("input_id")
    private String inputId;
    @JsonProperty("country")
    private String country;
    //private Language language; // future
    //private String features;   // future
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("administrative_area")
    private String administrativeArea;
    @JsonProperty("postal_code")
    private String postalCode;

    // Result set after API call
    private Candidate[] result;
    private Map<String, String> customParamMap;

    public Lookup() {
        this.result = new Candidate[0];
        this.customParamMap = new HashMap<>();
    }

    // region [Getters]
    public String getInputId() { return inputId; }
    public String getCountry() { return country; }
    //public Language getLanguage() { return language; } // future
    //public String getFeatures() { return features; } // future
    public String getLocality() { return locality; }
    public String getAdministrativeArea() { return administrativeArea; }
    public String getPostalCode() { return postalCode; }
    public Candidate[] getResult() { return result; }
    public Candidate getResult(int i) { return result[i]; }
    public Map<String, String> getCustomParamMap() { return customParamMap; }
    // endregion

    // region [Setters]
    public void setInputId(String inputId) { this.inputId = inputId; }
    public void setCountry(String country) { this.country = country; }
    //public void setLanguage(Language language) { this.language = language; } // future
    //public void setFeatures(String features) { this.features = features; } // future
    public void setLocality(String locality) { this.locality = locality; }
    public void setAdministrativeArea(String administrativeArea) { this.administrativeArea = administrativeArea; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setResult(Candidate[] candidates) { this.result = candidates; }
    public void addCustomParameter(String parameter, String value) { this.customParamMap.put(parameter, value); }
    // endregion
}
