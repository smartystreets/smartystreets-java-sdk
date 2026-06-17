package com.smartystreets.api.international_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RootLevel implements Serializable {

    //region [ Fields ]

    private String inputId;
    private String organization;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String address6;
    private String address7;
    private String address8;

    //endregion

    //region [ Getters ]

    @JsonProperty("input_id")
    public String getInputId() { return  inputId; }

    public String getOrganization() {
        return organization;
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

    public String getAddress5() {
        return address5;
    }

    public String getAddress6() {
        return address6;
    }

    public String getAddress7() {
        return address7;
    }

    public String getAddress8() {
        return address8;
    }

    //endregion
}
