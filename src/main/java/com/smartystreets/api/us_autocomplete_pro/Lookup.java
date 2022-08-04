package com.smartystreets.api.us_autocomplete_pro;

import com.smartystreets.api.GeolocateType;

import java.util.ArrayList;
import java.util.List;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#http-request-input-fields"
 */
public class Lookup {
    final int PREFER_RATIO_DEFAULT = 100;
    final int MAX_RESULTS_DEFAULT = 10;

    //region [ Fields ]

    private Suggestion[] result;
    private String search;
    private int maxResults;
    private List<String> cityFilter;
    private List<String> stateFilter;
    private List<String> zipcodeFilter;
    private List<String> excludeStates;
    private List<String> preferCity;
    private List<String> preferState;
    private List<String> preferZipcode;
    private int preferRatio;
    private GeolocateType preferGeolocation;
    private String selected;
    private String source;

    //endregion

    //region [ Constructors ]

    /**
     * If you use this constructor, don't forget to set the <b>prefix</b>. It is required.
     */
    public Lookup() {
        this.maxResults = this.MAX_RESULTS_DEFAULT;
        this.preferGeolocation = GeolocateType.CITY;
        this.cityFilter = new ArrayList<>();
        this.stateFilter = new ArrayList<>();
        this.zipcodeFilter = new ArrayList<>();
        this.excludeStates = new ArrayList<>();
        this.preferCity = new ArrayList<>();
        this.preferState = new ArrayList<>();
        this.preferZipcode = new ArrayList<>();
        this.preferRatio = this.PREFER_RATIO_DEFAULT;
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

    public Suggestion[] getResult() {
        return this.result;
    }

    public Suggestion getResult(int index) {
        return this.result[index];
    }

    public String getSearch() {
        return this.search;
    }

    public String getSelected() { return this.selected; }

    public String getSource() { return this.source; }

    public List<String> getCityFilter() {
        return this.cityFilter;
    }

    public List<String> getStateFilter() {
        return this.stateFilter;
    }

    public List<String> getZipcodeFilter() {
        return this.zipcodeFilter;
    }

    public List<String> getExcludeStates() {
        return this.excludeStates;
    }

    public List<String> getPreferCity() {
        return this.preferCity;
    }

    public List<String> getPreferState() { return this.preferState; }

    public List<String> getPreferZipcode() { return this.preferZipcode; }

    public int getPreferRatio() {
        return this.preferRatio;
    }

    String getPreferRatioStringIfSet() {
        if (this.preferRatio == this.PREFER_RATIO_DEFAULT)
            return null;
        return Integer.toString(this.preferRatio);
    }

    public GeolocateType getGeolocateType() {
        return preferGeolocation;
    }

    public int getMaxResults() {
        return maxResults;
    }

    String getMaxSuggestionsStringIfSet() {
        if (this.maxResults == this.MAX_RESULTS_DEFAULT)
            return null;
        return Integer.toString(this.maxResults);
    }

    //endregion

    //region [ Setters ]

    public void setResult(Suggestion[] result) {
        this.result = result;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setSelected(String selected) { this.selected = selected; }

    public void setSource(String source) { this.source = source; }

    public void setCityFilter(List<String> cityFilter) {
        this.cityFilter = cityFilter;
    }

    public void setStateFilter(List<String> stateFilter) {
        this.stateFilter = stateFilter;
    }

    public void setZipcodeFilter(List<String> zipcodeFilter) { this.zipcodeFilter = zipcodeFilter; }

    public void setExcludeStates(List<String> excludeStates) { this.excludeStates = excludeStates; }

    public void setPreferCity(List<String> cities) {
        this.preferCity = cities;
    }

    public void setPreferState(List<String> states) { this.preferState = states; }

    public void setPreferZipcode(List<String> zipcodes) { this.preferZipcode = zipcodes; }

    /***
     * Sets the percentage of suggestions that are to be from preferred cities/states.
     * @param preferRatio An integer value, range [0, 100]. Default is 33.
     * @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#preference"
     */
    public void setPreferRatio(int preferRatio) {
        this.preferRatio = preferRatio;
    }

    public void setGeolocateType(GeolocateType geolocateType) {
        this.preferGeolocation = geolocateType;
    }

    /***
     * Sets the maximum number of suggestions to return.
     * @param maxResults A positive integer range [1, 10]. Default is 10.
     * @throws IllegalArgumentException
     */
    public void setMaxResults(int maxResults) throws IllegalArgumentException{
        if (maxResults > 0 && maxResults <= this.MAX_RESULTS_DEFAULT) {
            this.maxResults = maxResults;
        } else {
            throw new IllegalArgumentException("Max results must be a positive integer no larger than 10.");
        }
    }

    public void addCityFilter(String city) {
        this.cityFilter.add(city);
    }

    public void addStateFilter(String stateAbbreviation) {
        this.stateFilter.add(stateAbbreviation);
    }

    public void addPreferCity(String city) {
        this.preferCity.add(city);
    }

    public void addPreferState(String state) { this.preferState.add(state); }

    public void addPreferZipcode(String zipcode) { this.preferZipcode.add(zipcode); }

    //endregion
}
