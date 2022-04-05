package com.smartystreets.api.international_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see "https://smartystreets.com/docs/cloud/international-street-api#components"
 */
public class Components implements Serializable {
    //region [ Fields ]

    private String countryIso3;
    private String superAdministrativeArea;
    private String administrativeArea;
    private String subAdministrativeArea;
    private String dependentLocality;
    private String dependentLocalityName;
    private String doubleDependentLocality;
    private String locality;
    private String postalCode;
    private String postalCodeShort;
    private String postalCodeExtra;
    private String premise;
    private String premiseExtra;
    private String premiseNumber;
    private String premisePrefixNumber;
    private String premiseType;
    private String thoroughfare;
    private String thoroughfarePredirection;
    private String thoroughfarePostdirection;
    private String thoroughfareName;
    private String thoroughfareTrailingType;
    private String thoroughfareType;
    private String dependentThoroughfare;
    private String dependentThoroughfarePredirection;
    private String dependentThoroughfarePostdirection;
    private String dependentThoroughfareName;
    private String dependentThoroughfareTrailingType;
    private String dependentThoroughfareType;
    private String building;
    private String buildingLeadingType;
    private String buildingName;
    private String buildingTrailingType;
    private String subBuildingType;
    private String subBuildingNumber;
    private String subBuildingName;
    private String subBuilding;
    private String postBox;
    private String postBoxType;
    private String postBoxNumber;

    //endregion

    //region [ Getters ]

    @JsonProperty("country_iso_3")
    public String getCountryIso3() {
        return countryIso3;
    }

    @JsonProperty("super_administrative_area")
    public String getSuperAdministrativeArea() {
        return superAdministrativeArea;
    }

    @JsonProperty("administrative_area")
    public String getAdministrativeArea() {
        return administrativeArea;
    }

    @JsonProperty("sub_administrative_area")
    public String getSubAdministrativeArea() {
        return subAdministrativeArea;
    }

    @JsonProperty("dependent_locality")
    public String getDependentLocality() {
        return dependentLocality;
    }

    @JsonProperty("dependent_locality_name")
    public String getDependentLocalityName() {
        return dependentLocalityName;
    }

    @JsonProperty("double_dependent_locality")
    public String getDoubleDependentLocality() {
        return doubleDependentLocality;
    }

    @JsonProperty("locality")
    public String getLocality() {
        return locality;
    }

    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postal_code_short")
    public String getPostalCodeShort() {
        return postalCodeShort;
    }

    @JsonProperty("postal_code_extra")
    public String getPostalCodeExtra() {
        return postalCodeExtra;
    }

    @JsonProperty("premise")
    public String getPremise() {
        return premise;
    }

    @JsonProperty("premise_extra")
    public String getPremiseExtra() {
        return premiseExtra;
    }

    @JsonProperty("premise_number")
    public String getPremiseNumber() {
        return premiseNumber;
    }

    @JsonProperty("premise_prefix_number")
    public String getPremisePrefixNumber() {
        return premisePrefixNumber;
    }

    @JsonProperty("premise_type")
    public String getPremiseType() {
        return premiseType;
    }

    @JsonProperty("thoroughfare")
    public String getThoroughfare() {
        return thoroughfare;
    }

    @JsonProperty("thoroughfare_predirection")
    public String getThoroughfarePredirection() {
        return thoroughfarePredirection;
    }

    @JsonProperty("thoroughfare_postdirection")
    public String getThoroughfarePostdirection() {
        return thoroughfarePostdirection;
    }

    @JsonProperty("thoroughfare_name")
    public String getThoroughfareName() {
        return thoroughfareName;
    }

    @JsonProperty("thoroughfare_trailing_type")
    public String getThoroughfareTrailingType() {
        return thoroughfareTrailingType;
    }

    @JsonProperty("thoroughfare_type")
    public String getThoroughfareType() {
        return thoroughfareType;
    }

    @JsonProperty("dependent_thoroughfare")
    public String getDependentThoroughfare() {
        return dependentThoroughfare;
    }

    @JsonProperty("dependent_thoroughfare_predirection")
    public String getDependentThoroughfarePredirection() {
        return dependentThoroughfarePredirection;
    }

    @JsonProperty("dependent_thoroughfare_postdirection")
    public String getDependentThoroughfarePostdirection() {
        return dependentThoroughfarePostdirection;
    }

    @JsonProperty("dependent_thoroughfare_name")
    public String getDependentThoroughfareName() {
        return dependentThoroughfareName;
    }

    @JsonProperty("dependent_thoroughfare_trailing_type")
    public String getDependentThoroughfareTrailingType() {
        return dependentThoroughfareTrailingType;
    }

    @JsonProperty("dependent_thoroughfare_type")
    public String getDependentThoroughfareType() {
        return dependentThoroughfareType;
    }

    @JsonProperty("building")
    public String getBuilding() {
        return building;
    }

    @JsonProperty("building_leading_type")
    public String getBuildingLeadingType() {
        return buildingLeadingType;
    }

    @JsonProperty("building_name")
    public String getBuildingName() {
        return buildingName;
    }

    @JsonProperty("building_trailing_type")
    public String getBuildingTrailingType() {
        return buildingTrailingType;
    }

    @JsonProperty("sub_building_type")
    public String getSubBuildingType() {
        return subBuildingType;
    }

    @JsonProperty("sub_building_number")
    public String getSubBuildingNumber() {
        return subBuildingNumber;
    }

    @JsonProperty("sub_building_name")
    public String getSubBuildingName() {
        return subBuildingName;
    }

    @JsonProperty("sub_building")
    public String getSubBuilding() {
        return subBuilding;
    }

    @JsonProperty("post_box")
    public String getPostBox() {
        return postBox;
    }

    @JsonProperty("post_box_type")
    public String getPostBoxType() {
        return postBoxType;
    }

    @JsonProperty("post_box_number")
    public String getPostBoxNumber() {
        return postBoxNumber;
    }

    //endregion
}
