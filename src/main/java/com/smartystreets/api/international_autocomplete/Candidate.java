package com.smartystreets.api.international_autocomplete;

import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/international-address-autocomplete-api#http-response"
 */
public class Candidate {
    //region [ Fields ]

    @Key("street")
    private String street;

    @Key("locality")
    private String locality;

    @Key("administrative_area")
    private String administrativeArea;

    @Key("postal_code")
    private String postalCode;

    @Key("country_iso3")
    private String countryISO3;

    //region [ Fields ]

    //region [ Getters ]

    public String getStreet() { return street; }

    public String getLocality() { return locality; }

    public String getAdministrativeArea() { return administrativeArea; }

    public String getPostalCode() { return postalCode; }

    public String getCountryISO3() { return countryISO3; }

    //endregion
}
