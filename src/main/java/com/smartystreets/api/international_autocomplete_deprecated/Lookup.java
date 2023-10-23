package com.smartystreets.api.international_autocomplete_deprecated;

import com.smartystreets.api.InternationalGeolocateType;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 * will contain the result of the lookup after it comes back from the API.
 *
 * @see "https://smartystreets.com/docs/cloud/international-address-autocomplete-api#http-request-input-fields"
 */
public class Lookup {
    static final int MAX_RESULTS_DEFAULT = 5;
    static final int DISTANCE_DEFAULT = 5;

    //region [ Fields ]

    private Candidate[] result;
    private String country;
    private String search;
    private int maxResults;
    private int distance;
    private InternationalGeolocateType geolocation;
    private String administrativeArea;
    private String locality;
    private String postalCode;
    private Float latitude;
    private Float longitude;
//endregion

    //region [ Constructors ]

    /**
     * If you use this constructor, don't forget to set the <b>prefix</b>. It is required.
     */
    public Lookup() {
        this.maxResults = MAX_RESULTS_DEFAULT;
        this.distance = DISTANCE_DEFAULT;
        this.geolocation = InternationalGeolocateType.NONE;
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

    public Candidate[] getResult() {
        return this.result;
    }

    public Candidate getResult(int index) {
        return this.result[index];
    }

    public String getCountry() {
        return this.country;
    }

    public String getSearch() {
        return this.search;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public int getDistance() {
        return this.distance;
    }

    public String getGeolocation() {
        return this.geolocation.getName();
    }

    public String getAdministrativeArea() {
        return this.administrativeArea;
    }

    public String getLocality() {
        return this.locality;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    //endregion

    //region [ Setters ]

    public void setResult(Candidate[] result) {
        this.result = result;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setGeolocation(InternationalGeolocateType geolocation) {
        this.geolocation = geolocation;
    }


    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    //endregion
}
