package com.smartystreets.api.international_street;

import com.google.api.client.util.Key;

public class Candidate {
    //region [ Fields ]

    @Key("organization")
    private String organization;

    @Key("address1")
    private String address1;

    @Key("address2")
    private String address2;

    @Key("address3")
    private String address3;

    @Key("address4")
    private String address4;

    @Key("address5")
    private String address5;

    @Key("address6")
    private String address6;

    @Key("address7")
    private String address7;

    @Key("address8")
    private String address8;

    @Key("address9")
    private String address9;

    @Key("address10")
    private String address10;

    @Key("address11")
    private String address11;

    @Key("address12")
    private String address12;

    @Key("components")
    private Components components;

    @Key("metadata")
    private Metadata metadata;

    @Key("analysis")
    private Analysis analysis;

    //endregion

    public Candidate() {
    }


    //region [ Getters ]

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

    public String getAddress9() {
        return address9;
    }

    public String getAddress10() {
        return address10;
    }

    public String getAddress11() {
        return address11;
    }

    public String getAddress12() {
        return address12;
    }

    public Components getComponents() {
        return components;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    //endregion
}
