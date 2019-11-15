package com.smartystreets.api.us_autocomplete_pro;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.smartystreets.api.GeolocateType;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#http-request-input-fields"
 */
public class Lookup {
    final double PREFER_RATIO_DEFAULT = 1/3.0;
    final int MAX_SUGGESTIONS_DEFAULT = 10;

    //region [ Fields ]

    private Suggestion[] result;
    private String search;
    private int maxSuggestions;
    private ArrayList<String> cityFilter;
    private ArrayList<String> stateFilter;
    private ArrayList<String> zipcodeFilter;
    private ArrayList<String> preferCity;
    private ArrayList<String> preferState;
    private ArrayList<String> preferZipcode;
    private double preferRatio;
    private GeolocateType preferGeolocation;

    //endregion

    //region [ Constructors ]

    /**
     * If you use this constructor, don't forget to set the <b>prefix</b>. It is required.
     */
    public Lookup() {
        this.maxSuggestions = this.MAX_SUGGESTIONS_DEFAULT;
        this.preferGeolocation = GeolocateType.CITY;
        this.cityFilter = new ArrayList<>();
        this.stateFilter = new ArrayList<>();
        this.zipcodeFilter = new ArrayList<>();
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

    public ArrayList<String> getCityFilter() {
        return this.cityFilter;
    }

    public ArrayList<String> getStateFilter() {
        return this.stateFilter;
    }

    public ArrayList<String> getPreferCity() {
        return this.preferCity;
    }

    public ArrayList<String> getPreferState() { return this.preferState; }

    public ArrayList<String> getPreferZipcode() { return this.preferZipcode; }

    public double getPreferRatio() {
        return this.preferRatio;
    }

    String getPreferRatioStringIfSet() {
        if (this.preferRatio == this.PREFER_RATIO_DEFAULT)
            return null;
        return Double.toString(this.preferRatio);
    }

    public GeolocateType getGeolocateType() {
        return preferGeolocation;
    }

    public int getMaxSuggestions() {
        return maxSuggestions;
    }

    String getMaxSuggestionsStringIfSet() {
        if (this.maxSuggestions == this.MAX_SUGGESTIONS_DEFAULT)
            return null;
        return Integer.toString(this.maxSuggestions);
    }

    //endregion

    //region [ Setters ]

    public void setResult(Suggestion[] result) {
        this.result = result;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setCityFilter(ArrayList<String> cityFilter) {
        this.cityFilter = cityFilter;
    }

    public void setStateFilter(ArrayList<String> stateFilter) {
        this.stateFilter = stateFilter;
    }

    public void setZipcodeFilter(ArrayList<String> zipcodeFilter) { this.zipcodeFilter = zipcodeFilter ;}

    public void setPreferCity(ArrayList<String> cities) {
        this.preferCity = cities;
    }

    public void setPreferState(ArrayList<String> states) { this.preferState = states; }

    public void setPreferZipcode(ArrayList<String> zipcodes) { this.preferZipcode = zipcodes; }

    /***
     * Sets the percentage of suggestions that are to be from preferred cities/states.
     * @param preferRatio A decimal value, range [0, 1]. Default is 0.333333333.
     * @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#preference"
     */
    public void setPreferRatio(double preferRatio) {
        this.preferRatio = preferRatio;
    }

    public void setGeolocateType(GeolocateType geolocateType) {
        this.preferGeolocation = geolocateType;
    }

    /***
     * Sets the maximum number of suggestions to return.
     * @param maxSuggestions A positive integer range [1, 10]. Default is 10.
     * @throws IllegalArgumentException
     */
    public void setMaxSuggestions(int maxSuggestions) throws IllegalArgumentException{
        if (maxSuggestions > 0 && maxSuggestions <= this.MAX_SUGGESTIONS_DEFAULT) {
            this.maxSuggestions = maxSuggestions;
        } else {
            throw new IllegalArgumentException("Max suggestions must be a positive integer no larger than 10.");
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
