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

    //region [ Components Helpers ]

    public String getUrbanization() {
        return this.components.getUrbanization();
    }

    public String getPrimaryNumber() {
        return this.components.getPrimaryNumber();
    }

    public String getStreetName() {
        return this.components.getStreetName();
    }

    public String getStreetPredirection() {
        return this.components.getStreetPredirection();
    }

    public String getStreetPostdirection() {
        return this.components.getStreetPostdirection();
    }

    public String getStreetSuffix() {
        return this.components.getStreetSuffix();
    }

    public String getSecondaryNumber() {
        return this.components.getSecondaryNumber();
    }

    public String getSecondaryDesignator() {
        return this.components.getSecondaryDesignator();
    }

    public String getExtraSecondaryNumber() {
        return this.components.getExtraSecondaryNumber();
    }

    public String getExtraSecondaryDesignator() {
        return this.components.getExtraSecondaryDesignator();
    }

    public String getPmbDesignator() {
        return this.components.getPmbDesignator();
    }

    public String getPmbNumber() {
        return this.components.getPmbNumber();
    }

    public String getCityName() {
        return this.components.getCityName();
    }

    public String getDefaultCityName() {
        return this.components.getDefaultCityName();
    }

    public String getState() {
        return this.components.getState();
    }

    public String getZipCode() {
        return this.components.getZipCode();
    }

    public String getPlus4Code() {
        return this.components.getPlus4Code();
    }

    public String getDeliveryPoint() {
        return this.components.getDeliveryPoint();
    }

    public String getDeliveryPointCheckDigit() {
        return this.components.getDeliveryPointCheckDigit();
    }

    //endregion

    //region [ Metadata Helpers ]

    public String getRecordType() {
        return this.metadata.getRecordType();
    }

    public String getZipType() {
        return this.metadata.getZipType();
    }

    public String getCountyFips() {
        return this.metadata.getCountyFips();
    }

    public String getCountyName() {
        return this.metadata.getCountyName();
    }

    public String getCarrierRoute() {
        return this.metadata.getCarrierRoute();
    }

    public String getCongressionalDistrict() {
        return this.metadata.getCongressionalDistrict();
    }

    public String getBuildingDefaultIndicator() {
        return this.metadata.getBuildingDefaultIndicator();
    }

    public String getRdi() {
        return this.metadata.getRdi();
    }

    public String getElotSequence() {
        return this.metadata.getElotSequence();
    }

    public String getElotSort() {
        return this.metadata.getElotSort();
    }

    public double getLatitude() {
        return this.metadata.getLatitude();
    }

    public double getLongitude() {
        return this.metadata.getLongitude();
    }

    public String getPrecision() {
        return this.metadata.getPrecision();
    }

    public String getTimeZone() {
        return this.metadata.getTimeZone();
    }

    public double getUtcOffset() {
        return this.metadata.getUtcOffset();
    }

    public boolean usesDst() {
        return this.metadata.obeysDst();
    }

    //endregion

    //region [ Analysis Getters ]

    public String getDpvMatchCode() {
        return this.analysis.getDpvMatchCode();
    }

    public String getDpvFootnotes() {
        return this.analysis.getDpvFootnotes();
    }

    public String getCmra() {
        return this.analysis.getCmra();
    }

    public String getVacant() {
        return this.analysis.getVacant();
    }

    public String getActive() {
        return this.analysis.getActive();
    }

    public boolean isEwsMatch() {
        return this.analysis.isEwsMatch();
    }

    public String getFootnotes() {
        return this.analysis.getFootnotes();
    }

    public String getLacsLinkCode() {
        return this.analysis.getLacsLinkCode();
    }

    public String getLacsLinkIndicator() {
        return this.analysis.getLacsLinkIndicator();
    }

    public boolean isSuiteLinkMatch() {
        return this.analysis.isSuiteLinkMatch();
    }

    public boolean isValid() {
        String value = this.analysis.getDpvMatchCode();
        return (value != null && (value.equals("Y") || value.equals("S") || value.equals("D")));
    }

    public boolean Cmra() {
        String value = this.analysis.getCmra();
        return (value != null && value.equals("Y"));
    }

    public boolean isVacant() {
        String value = this.analysis.getVacant();
        return (value != null && value.equals("Y"));
    }

    public boolean isActive() {
        String value = this.analysis.getActive();
        return (value != null && value.equals("Y"));
    }

    //endregion
}
