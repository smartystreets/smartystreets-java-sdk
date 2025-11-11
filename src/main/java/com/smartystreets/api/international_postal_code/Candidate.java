package com.smartystreets.api.international_postal_code;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * Represents a single result candidate for an international postal code lookup.
 * @see "https://smartystreets.com/docs/international-postal-code-api"
 */
public class Candidate implements Serializable {
    @JsonProperty("input_id")
    private String inputId;
    @JsonProperty("administrative_area")
    private String administrativeArea;
    @JsonProperty("sub_administrative_area")
    private String subAdministrativeArea;
    @JsonProperty("super_administrative_area")
    private String superAdministrativeArea;
    @JsonProperty("country_iso_3")
    private String countryIso3;
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("dependent_locality")
    private String dependentLocality;
    @JsonProperty("dependent_locality_name")
    private String dependentLocalityName;
    @JsonProperty("double_dependent_locality")
    private String doubleDependentLocality;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("postal_code_extra")
    private String postalCodeExtra;

    // region [Getters]
    public String getInputId() { return inputId; }
    public String getAdministrativeArea() { return administrativeArea; }
    public String getSubAdministrativeArea() { return subAdministrativeArea; }
    public String getSuperAdministrativeArea() { return superAdministrativeArea; }
    public String getCountryIso3() { return countryIso3; }
    public String getLocality() { return locality; }
    public String getDependentLocality() { return dependentLocality; }
    public String getDependentLocalityName() { return dependentLocalityName; }
    public String getDoubleDependentLocality() { return doubleDependentLocality; }
    public String getPostalCode() { return postalCode; }
    public String getPostalCodeExtra() { return postalCodeExtra; }
    // endregion
}
