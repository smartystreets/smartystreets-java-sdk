package com.smartystreets.api.us_autocomplete;

import java.util.ArrayList;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/us-autocomplete-api#http-request-input-fields"
 */
public class Lookup {
    //region [ Fields ]

    private Suggestion[] result;
    private String prefix;
    private int maxSuggestions;
    private ArrayList<String> cityFilter;
    private ArrayList<String> stateFilter;
    private ArrayList<String> prefer;
    private GeolocateType geolocateType;

    //endregion

    //region [ Constructors ]

    public Lookup() {
        this.maxSuggestions = 10;
        this.geolocateType = GeolocateType.CITY;
        this.cityFilter = new ArrayList<>();
        this.stateFilter = new ArrayList<>();
        this.prefer = new ArrayList<>();
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
        return prefix;
    }

    public ArrayList<String> getCityFilter() {
        return cityFilter;
    }

    public ArrayList<String> getStateFilter() {
        return stateFilter;
    }

    public ArrayList<String> getPrefer() {
        return prefer;
    }

    public GeolocateType getGeolocateType() {
        return geolocateType;
    }

    public int getMaxSuggestions() {
        return maxSuggestions;
    }

    //endregion

    //region [ Setters ]

    public void setResult(Suggestion[] result) {
        this.result = result;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setCityFilter(ArrayList<String> cityFilter) {
        this.cityFilter = cityFilter;
    }

    public void setStateFilter(ArrayList<String> stateFilter) {
        this.stateFilter = stateFilter;
    }

    public void setPrefer(ArrayList<String> prefer) {
        this.prefer = prefer;
    }

    public void setGeolocateType(GeolocateType geolocateType) {
        this.geolocateType = geolocateType;
    }


    public void setMaxSuggestions(int maxSuggestions) throws IllegalArgumentException{
        if (maxSuggestions > 0 && maxSuggestions <= 10) {
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
