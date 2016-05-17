package com.smartystreets.api.us_street;

import com.google.api.client.util.Key;

public class Candidate {
    //region [ Fields ]

    @Key("input_id")
    private String inputId;

    @Key("input_index")
    private int inputIndex;

    @Key("candidate_index")
    private int candidateIndex;

    @Key("addressee")
    private String addressee;

    @Key("delivery_line_1")
    private String deliveryLine1;

    @Key("delivery_line_2")
    private String deliveryLine2;

    @Key("last_line")
    private String lastLine;

    @Key("delivery_point_barcode")
    private String deliveryPointBarcode;

    @Key("components")
    private Components components;

    @Key("metadata")
    private Metadata metadata;

    @Key("analysis")
    private Analysis analysis;

    //endregion

    //region [ Getters ]

    public Components getComponents() {
        return this.components;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public Analysis getAnalysis() {
        return this.analysis;
    }

    public String getInputId() {
        return this.inputId;
    }

    public int getInputIndex() {
        return this.inputIndex;
    }

    public int getCandidateIndex() {
        return this.candidateIndex;
    }

    public String getAddressee() {
        return this.addressee;
    }

    public String getDeliveryLine1() {
        return this.deliveryLine1;
    }

    public String getDeliveryLine2() {
        return this.deliveryLine2;
    }

    public String getLastLine() {
        return this.lastLine;
    }

    public String getDeliveryPointBarcode() {
        return this.deliveryPointBarcode;
    }

    //endregion
}
