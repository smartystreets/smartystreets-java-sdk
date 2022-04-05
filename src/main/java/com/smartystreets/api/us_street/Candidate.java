package com.smartystreets.api.us_street;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * A candidate is a possible match for an address that was submitted.<br>
 *     A lookup can have multiple candidates if the address was ambiguous, and<br>
 *     the maxCandidates field is set higher than 1.
 *
 * @see "https://smartystreets.com/docs/cloud/us-street-api#root"
 */
public class Candidate implements Serializable {
    //region [ Fields ]

    private String inputId;
    private int inputIndex;
    private int candidateIndex;
    private String addressee;
    private String deliveryLine1;
    private String deliveryLine2;
    private String lastLine;
    private String deliveryPointBarcode;
    private Components components;
    private Metadata metadata;
    private Analysis analysis;

    //endregion


    public Candidate() {
    }

    public Candidate(int inputIndex) {
        this.inputIndex = inputIndex;
    }


    //region [ Getters ]

    @JsonProperty("components")
    public Components getComponents() {
        return this.components;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return this.metadata;
    }

    @JsonProperty("analysis")
    public Analysis getAnalysis() {
        return this.analysis;
    }

    @JsonProperty("input_id")
    public String getInputId() { return this.inputId; }

    @JsonProperty("input_index")
    public int getInputIndex() {
        return this.inputIndex;
    }

    @JsonProperty("candidate_index")
    public int getCandidateIndex() {
        return this.candidateIndex;
    }

    @JsonProperty("addressee")
    public String getAddressee() {
        return this.addressee;
    }

    @JsonProperty("delivery_line_1")
    public String getDeliveryLine1() {
        return this.deliveryLine1;
    }

    @JsonProperty("delivery_line_2")
    public String getDeliveryLine2() {
        return this.deliveryLine2;
    }

    @JsonProperty("last_line")
    public String getLastLine() {
        return this.lastLine;
    }

    @JsonProperty("delivery_point_barcode")
    public String getDeliveryPointBarcode() {
        return this.deliveryPointBarcode;
    }

    //endregion
}
