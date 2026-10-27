package com.smartystreets.api.international_postal_code;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private LanguageMode language;
    //private String features;   // future
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("administrative_area")
    private String administrativeArea;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("max_results")
    private int maxResults;

    // Result set after API call
    private Candidate[] result;

    public Lookup() {
        this.result = new Candidate[0];
    }

    // region [Getters]
    public String getInputId() { return inputId; }
    public String getCountry() { return country; }
    public LanguageMode getLanguage() { return language; }
    //public String getFeatures() { return features; } // future
    public String getLocality() { return locality; }
    public String getAdministrativeArea() { return administrativeArea; }
    public String getPostalCode() { return postalCode; }
    public int getMaxResults() { return maxResults; }
    public Candidate[] getResult() { return result; }
    public Candidate getResult(int i) { return result[i]; }
    // endregion

    // region [Setters]
    public void setInputId(String inputId) { this.inputId = inputId; }
    public void setCountry(String country) { this.country = country; }
    public void setLanguage(LanguageMode language) { this.language = language; }
    //public void setFeatures(String features) { this.features = features; } // future
    public void setLocality(String locality) { this.locality = locality; }
    public void setAdministrativeArea(String administrativeArea) { this.administrativeArea = administrativeArea; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setMaxResults(int maxResults) { this.maxResults = maxResults; }
    public void setResult(Candidate[] candidates) { this.result = candidates; }
    // endregion

    String getMaxResultsStringIfSet() {
        if (this.maxResults <= 0)
            return null;
        return Integer.toString(this.maxResults);
    }
}
