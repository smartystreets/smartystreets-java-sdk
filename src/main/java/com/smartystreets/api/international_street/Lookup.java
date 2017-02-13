package com.smartystreets.api.international_street;

public class Lookup {
    //region [ Fields ]
    private Candidate[] result;

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

    //region [ Constructors ]

    public Lookup() {
        this.result = new Candidate[0];
    }

    public Lookup(String freeform, String country) {
        this();
        this.freeform = freeform;
        this.country = country;
    }

    public Lookup(String address1, String postalCode, String country) {
        this();
        this.address1 = address1;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Lookup(String address1, String locality, String administrativeArea, String country) {
        this();
        this.address1 = address1;
        this.locality = locality;
        this.administrativeArea = administrativeArea;
        this.country = country;
    }

    //endregion

    //region [ Getters ]

    public Candidate[] getResult() {
        return result;
    }

    public Candidate getResult(int index) {
        return result[index];
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

    public void setResult(Candidate[] result) {
        this.result = result;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGeocode(boolean geocode) {
        this.geocode = String.valueOf(geocode);
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
