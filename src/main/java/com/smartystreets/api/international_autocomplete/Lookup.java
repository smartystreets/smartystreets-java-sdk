package com.smartystreets.api.international_autocomplete;

import java.util.HashMap;
import java.util.Map;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 * will contain the result of the lookup after it comes back from the API.
 *
 * @see "https://smartystreets.com/docs/cloud/international-address-autocomplete-api#http-request-input-fields"
 */
public class Lookup {
    static final int MAX_RESULTS_DEFAULT = 5;
    static final int DISTANCE_DEFAULT = 5;

    //region [ Fields ]

    private Candidate[] result;
    private String country;
    private String search;
    private String addressID;
    private int maxResults;
    private String locality;
    private String postalCode;
    private Map<String, String> customParamMap;

//endregion

    //region [ Constructors ]

    /**
     * If you use this constructor, don't forget to set the <b>prefix</b>. It is required.
     */
    public Lookup() {
        this.maxResults = MAX_RESULTS_DEFAULT;
        this.customParamMap = new HashMap<>();
    }

    /**
     * @param search The beginning of an address
     */
    public Lookup(String search) {
        this();
        this.search = search;
    }

    //endregion

    //region [ Getters ]

    public Candidate[] getResult() {
        return this.result;
    }

    public Candidate getResult(int index) {
        return this.result[index];
    }

    public String getCountry() {
        return this.country;
    }

    public String getSearch() {
        return this.search;
    }

    public String getAddressID() {
        return addressID;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public String getLocality() {
        return this.locality;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Map<String, String> getCustomParamMap() {
        return this.customParamMap;
    }

    //endregion

    //region [ Setters ]
    public void setResult(Candidate[] result) {
        this.result = result;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void addCustomParameter(String parameter, String value) {
        this.customParamMap.put(parameter, value);
    }

    //endregion
}
