package com.smartystreets.api.international_street;

import java.util.ArrayList;

public class Lookup {
    //region [ Fields ]
    private ArrayList<Candidate> result;

    private String inputId;
    private String country;
    private String geocode;
    private LanguageMode language;
    private String freeform;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String organization;
    private String locality;
    private String administrativeArea;
    private String postalCode;

    //endregion

    public void addToResult(Candidate newCandidate) {
        this.result.add(newCandidate);
    }

    //region [ Getters ]

    public ArrayList<Candidate> getResult() {
        return result;
    }

    public Candidate getResult(int index) {
        return result.get(index);
    }

    public String getInputId() {
        return inputId;
    }

    public String getCountry() {
        return country;
    }

    public String getGeocode() {
        return geocode;
    }

    public LanguageMode getLanguage() {
        return language;
    }

    public String getFreeform() {
        return freeform;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getAddress4() {
        return address4;
    }

    public String getOrganization() {
        return organization;
    }

    public String getLocality() {
        return locality;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public String getPostalCode() {
        return postalCode;
    }

    //endregion

    //region [ Setters ]

    public void setResult(ArrayList<Candidate> result) {
        this.result = result;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public void setLanguage(LanguageMode language) {
        this.language = language;
    }

    public void setFreeform(String freeform) {
        this.freeform = freeform;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    //endregion
}
