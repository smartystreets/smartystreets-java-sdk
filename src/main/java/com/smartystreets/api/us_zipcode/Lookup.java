package com.smartystreets.api.us_zipcode;

import javax.json.JsonObject;

public class Lookup {
    private Result result;
    private String inputId;
    private String city;
    private String state;
    private String zipcode;

    public Lookup() {
        this.result = new Result();
    }

    public Lookup(String zipcode) {
        this();
        this.zipcode = zipcode;
    }

    public Lookup(String city, String state) {
        this();
        this.city = city;
        this.state = state;
    }

    public Lookup(String city, String state, String zipcode) {
        this();
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    /**** Getters ********************************************************************************/

    public Result getResult() {
        return this.result;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public String getInputId() {
        return this.inputId;
    }

    /**** Setters ********************************************************************************/

    public void setResult(Result result) {
        this.result = result;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Lookup setInputId(String inputId) {
        this.inputId = inputId;
        return this;
    }
}
