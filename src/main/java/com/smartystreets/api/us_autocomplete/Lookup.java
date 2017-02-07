package com.smartystreets.api.us_autocomplete;

import java.util.ArrayList;


public class Lookup {
    //region [ Fields ]

    private Result result;

    private String prefix;

    private int maxSuggestions;

    private ArrayList<String> cityFilter;

    private ArrayList<String> stateFilter;

    private ArrayList<String> prefer;

    private boolean geolocate;

    private String geolocatePrecision;

    //endregion

    //region [ Constructors ]

    public Lookup() {
        this.maxSuggestions = 10;
        this.geolocate = true;
        this.result = new Result();
        this.cityFilter = new ArrayList<>();
        this.stateFilter = new ArrayList<>();
        this.prefer = new ArrayList<>();
    }

    public Lookup(String prefix) {
        this();
        this.prefix = prefix;
    }

    //endregion

    //region [ Getters ]

    public Result getResult() {
        return this.result;
    }

    public Suggestion getResult(int index) {
        return this.result.getSuggestions()[index];
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

    public boolean getGeolocate() {
        return geolocate;
    }

    public String getGeolocatePrecision() {
        return geolocatePrecision;
    }

    public int getMaxSuggestions() {
        return maxSuggestions;
    }

    //endregion

    //region [ Setters ]

    public void setResult(Result result) {
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

    public void setGeolocate(boolean geolocate) {
        this.geolocate = geolocate;
    }

    public void setGeolocatePrecision(String geolocatePrecision) {
        this.geolocatePrecision = geolocatePrecision;
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
