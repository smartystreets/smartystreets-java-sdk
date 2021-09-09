package com.smartystreets.api.us_autocomplete;

import java.util.ArrayList;
import java.util.List;
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
    private String prefix;
    private int maxSuggestions;
    private List<String> cityFilter;
    private List<String> stateFilter;
    private List<String> prefer;
    private double preferRatio;
    private GeolocateType geolocateType;

    //endregion

    //region [ Constructors ]

    /**
     * If you use this constructor, don't forget to set the <b>prefix</b>. It is required.
     */
    public Lookup() {
        this.maxSuggestions = this.MAX_SUGGESTIONS_DEFAULT;
        this.geolocateType = GeolocateType.CITY;
        this.cityFilter = new ArrayList<>();
        this.stateFilter = new ArrayList<>();
        this.prefer = new ArrayList<>();
        this.preferRatio = this.PREFER_RATIO_DEFAULT;
    }

    /**
     * @param prefix The beginning of an address
     */
    public Lookup(String prefix) {
        this();
        this.prefix = prefix;
    }

    //endregion

    //region [ Getters ]

    public Suggestion[] getResult() {
        return this.result;
    }

    public Suggestion getResult(int index) {
        return this.result[index];
    }

    public String getPrefix() {
        return this.prefix;
    }

    public List<String> getCityFilter() {
        return this.cityFilter;
    }

    public List<String> getStateFilter() {
        return this.stateFilter;
    }

    public List<String> getPrefer() {
        return this.prefer;
    }

    public double getPreferRatio() {
        return this.preferRatio;
    }

    String getPreferRatioStringIfSet() {
        if (this.preferRatio == this.PREFER_RATIO_DEFAULT)
            return null;
        return Double.toString(this.preferRatio);
    }

    public GeolocateType getGeolocateType() {
        return geolocateType;
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

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setCityFilter(List<String> cityFilter) {
        this.cityFilter = cityFilter;
    }

    public void setStateFilter(List<String> stateFilter) {
        this.stateFilter = stateFilter;
    }

    public void setPrefer(List<String> prefer) {
        this.prefer = prefer;
    }

    /***
     * Sets the percentage of suggestions that are to be from preferred cities/states.
     * @param preferRatio A decimal value, range [0, 1]. Default is 0.333333333.
     * @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#preference"
     */
    public void setPreferRatio(double preferRatio) {
        this.preferRatio = preferRatio;
    }

    public void setGeolocateType(GeolocateType geolocateType) {
        this.geolocateType = geolocateType;
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

    public void addPrefer(String cityOrState) {
        this.prefer.add(cityOrState);
    }

    //endregion
}
