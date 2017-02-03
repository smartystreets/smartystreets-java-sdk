package com.smartystreets.api.us_autocomplete;

import com.google.api.client.util.Key;


public class Lookup {
    //region [ Fields ]

    private Result result;

    @Key("prefix")
    private String prefix;

    @Key("suggestions")
    private int maxSuggestions;

    @Key("city_filter")
    private String cityFilter;

    @Key("state_filter")
    private String stateFilter;

    @Key("prefer")
    private String prefer;

    @Key("geolocate")
    private boolean geolocate;

    @Key("geolocate_precision")
    private String geolocatePrecision;

    //endregion

    //region [ Constructors ]

    public Lookup() {
        this.maxSuggestions = 10;
        this.geolocate = true;
        this.result = new Result();
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

    public String getCityFilter() {
        return cityFilter;
    }

    public String getStateFilter() {
        return stateFilter;
    }

    public String getPrefer() {
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

    public void setCityFilter(String cityFilter) {
        this.cityFilter = cityFilter;
    }

    public void setStateFilter(String stateFilter) {
        this.stateFilter = stateFilter;
    }

    public void setPrefer(String prefer) {
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


    //endregion
}
