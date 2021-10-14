package com.smartystreets.api.international_autocomplete;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/international-address-autocomplete-api#http-request-input-fields"
 */
public class Lookup {
    //region [ Fields ]

    private Candidate[] result;
    private String country;
    private String search;
    private String administrativeArea;
    private String locality;
    private String postalCode;

    //endregion

    //region [ Constructors ]

    /**
     * If you use this constructor, don't forget to set the <b>prefix</b>. It is required.
     */
    public Lookup() {}

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

    public String getCountry() { return this.country; }

    public String getSearch() { return this.search; }

    public String getAdministrativeArea() { return this.administrativeArea; }

    public String getLocality() { return this.locality; }

    public String getPostalCode() { return this.postalCode; }

    //endregion

    //region [ Setters ]

    public void setResult(Candidate[] result) {
        this.result = result;
    }

    public void setCountry(String country) { this.country = country; }

    public void setSearch(String search) { this.search = search; }

    public void setAdministrativeArea(String administrativeArea) { this.administrativeArea = administrativeArea; }

    public void setLocality(String locality) { this.locality = locality; }

    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    //endregion
}
