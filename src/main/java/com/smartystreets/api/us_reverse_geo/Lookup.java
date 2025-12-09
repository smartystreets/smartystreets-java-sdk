package com.smartystreets.api.us_reverse_geo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Lookup implements Serializable {

    //region [ Fields ]

    private double latitude;
    private double longitude;
    private String source;

    private SmartyResponse response;
    private Map<String, String> customParamMap;

    //endregion

    public Lookup() {
        this.response = new SmartyResponse();
        this.customParamMap = new HashMap<>();
    }

    public Lookup(double latitude, double longitude) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() { return this.latitude; }

    public Double getLongitude() { return this.longitude; }

    public String getSource() { return this.source; }

    public void setSource(String source) { this.source = source; }

    public SmartyResponse getResponse() { return this.response; }

    public void setResponse(SmartyResponse response) {
        this.response = response;
    }

    public Map<String, String> getCustomParamMap() {
        return this.customParamMap;
    }

    public void addCustomParameter(String parameter, String value) {
        this.customParamMap.put(parameter, value);
    }
}
