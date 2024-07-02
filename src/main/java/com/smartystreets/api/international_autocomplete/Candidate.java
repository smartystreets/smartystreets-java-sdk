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
    private String administrativeAreaShort;
    private String administrativeAreaLong;
    private String postalCode;
    private String countryISO3;

    private int entries;
    private String addressText;
    private String addressID;

    //region [ Fields ]

    //region [ Getters ]

    public String getStreet() {
        return street;
    }

    public String getLocality() {
        return locality;
    }

    @JsonProperty("administrative_area")
    public String getAdministrativeArea() {
        return administrativeArea;
    }

    @JsonProperty("administrative_area_short")
    public String getAdministrativeAreaShort() {
        return administrativeAreaShort;
    }

    @JsonProperty("administrative_area_long")
    public String getAdministrativeAreaLong() {
        return administrativeAreaLong;
    }

    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("country_iso3")
    public String getCountryISO3() {
        return countryISO3;
    }

    @JsonProperty("entries")
    public int getEntries() {
        return entries;
    }

    @JsonProperty("address_text")
    public String getAddressText() {
        return addressText;
    }

    @JsonProperty("address_id")
    public String getAddressID() {
        return addressID;
    }

    //endregion
}
