package com.smartystreets.api.international_autocomplete;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/international-address-autocomplete-api#http-response"
 */
public class Candidate implements Serializable {
    //region [ Fields ]

    private String street;
    private String locality;
    private String administrativeArea;
    private String postalCode;
    private String countryISO3;

    //region [ Fields ]

    //region [ Getters ]

    public String getStreet() { return street; }

    public String getLocality() { return locality; }

    @JsonProperty("administrative_area")
    public String getAdministrativeArea() { return administrativeArea; }

    @JsonProperty("postal_code")
    public String getPostalCode() { return postalCode; }

    @JsonProperty("country_iso3")
    public String getCountryISO3() { return countryISO3; }

    //endregion
}
