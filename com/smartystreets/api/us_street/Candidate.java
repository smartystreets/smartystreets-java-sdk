package com.smartystreets.api.us_street;

/**
 * Created by Neo on 4/19/16.
 */

import javax.json.JsonNumber;
import javax.json.JsonObject;

public class Candidate {
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

    public Candidate(JsonObject obj) {
        JsonObject components = obj.getJsonObject("components");
        JsonObject metadata = obj.getJsonObject("metadata");
        JsonObject analysis = obj.getJsonObject("analysis");
        this.inputId = obj.getString("input_id", null);
        this.inputIndex = obj.getInt("input_index", 0);
        this.candidateIndex = obj.getInt("candidate_index", 0);
        this.addressee = obj.getString("addressee", null);
        this.deliveryLine1 = obj.getString("delivery_line_1", null);
        this. deliveryLine2 = obj.getString("delivery_line_2", null);
        this. lastLine = obj.getString("last_line", null);
        this. deliveryPointBarcode = obj.getString("delivery_point_barcode", null);

        this.components = new Components();
        this.components.urbanization = components.getString("urbanization", null);
        this.components.primaryNumber = components.getString("primary_number", null);
        this.components.streetName = components.getString("street_name", null);
        this.components.streetPredirection = components.getString("street_predirection", null);
        this.components.streetPostdirection = components.getString("street_postdirection", null);
        this.components.streetSuffix = components.getString("street_suffix", null);
        this.components.secondaryNumber = components.getString("secondary_number", null);
        this.components.secondaryDesignator = components.getString("secondary_designator", null);
        this.components.extraSecondaryNumber = components.getString("extra_secondary_number", null);
        this.components.extraSecondaryDesignator = components.getString("extra_secondary_designator", null);
        this.components.PMBDesignator = components.getString("pmb_designator", null);
        this.components.PMBNumber = components.getString("pmb_number", null);
        this.components.cityName = components.getString("city_name", null);
        this.components.defaultCityName = components.getString("default_city_name", null);
        this.components.state = components.getString("state_abbreviation", null);
        this.components.ZIPCode = components.getString("zipcode", null);
        this.components.plus4Code = components.getString("plus4_code", null);
        this.components.deliveryPoint = components.getString("delivery_point", null);
        this.components.deliveryPointCheckDigit = components.getString("delivery_point_check_digit", null);

        this.metadata = new Metadata();
        this.metadata.recordType = metadata.getString("record_type", null);
        this.metadata.ZIPType =  metadata.getString("zip_type", null);
        this.metadata.countyFIPS = metadata.getString("county_fips", null);
        this.metadata.countyName = metadata.getString("county_name", null);
        this.metadata.carrierRoute = metadata.getString("carrier_route", null);
        this.metadata.congressionalDistrict = metadata.getString("congressional_district", null);
        this.metadata.buildingDefaultIndicator = metadata.getString("building_default_indicator", null);
        this.metadata.RDI = metadata.getString("rdi", null);
        this.metadata.ELOTSequence = metadata.getString("elot_sequence", null);
        this.metadata.ELOTSort = metadata.getString("elot_sort", null);
        JsonNumber number = metadata.getJsonNumber("latitude");
        this.metadata.latitude = number == null ? 0 : number.doubleValue();
        number = metadata.getJsonNumber("longitude");
        this.metadata.longitude = number == null ? 0 : number.doubleValue();
        this.metadata.precision = metadata.getString("precision", null);
        this.metadata.timeZone = metadata.getString("time_zone", null);
        number = metadata.getJsonNumber("utc_offset");
        this.metadata.UTCOffset = number == null ? 0 : number.doubleValue();
        String value = metadata.getString("dst", null);
        this.metadata.usesDST = (value != null && value.equals("true"));

        this.analysis = new Analysis();
        this.analysis.DPVMatchCode = analysis.getString("dpv_match_code", null);
        this.analysis.DPVFootnotes = analysis.getString("dpv_footnotes", null);
        this.analysis.CMRA = analysis.getString("dpv_cmra", null);
        this.analysis.vacant = analysis.getString("dpv_vacant", null);
        this.analysis.active = analysis.getString("active", null);
        value = analysis.getString("ews_match", null);
        this.analysis.isEWSMatch = (value != null && value.equals("true"));
        this.analysis.footnotes = analysis.getString("footnotes", null);
        this.analysis.LACSLinkCode = analysis.getString("lacslink_code", null);
        this.analysis.LACSLinkIndicator = analysis.getString("lacslink_indicator", null);
        value = analysis.getString("suitelink_match", null);
        this.analysis.isSuiteLinkMatch = (value != null && value.equals("true"));
    }


    private class Components {
        private String urbanization;
        private String primaryNumber;
        private String streetName;
        private String streetPredirection;
        private String streetPostdirection;
        private String streetSuffix;
        private String secondaryNumber;
        private String secondaryDesignator;
        private String extraSecondaryNumber;
        private String extraSecondaryDesignator;
        private String PMBDesignator;
        private String PMBNumber;
        private String cityName;
        private String defaultCityName;
        private String state;
        private String ZIPCode;
        private String plus4Code;
        private String deliveryPoint;
        private String deliveryPointCheckDigit;

        public String getUrbanization() {
            return this.urbanization;
        }

        public String getPrimaryNumber() {
            return this.primaryNumber;
        }

        public String getStreetName() {
            return this.streetName;
        }

        public String getStreetPredirection() {
            return this.streetPredirection;
        }

        public String getStreetPostdirection() {
            return this.streetPostdirection;
        }

        public String getStreetSuffix() {
            return this.streetSuffix;
        }

        public String getSecondaryNumber() {
            return this.secondaryNumber;
        }

        public String getSecondaryDesignator() {
            return this.secondaryDesignator;
        }

        public String getExtraSecondaryNumber() {
            return this.extraSecondaryNumber;
        }

        public String getExtraSecondaryDesignator() {
            return this.extraSecondaryDesignator;
        }

        public String getPMBDesignator() {
            return this.PMBDesignator;
        }

        public String getPMBNumber() {
            return this.PMBNumber;
        }

        public String getCityName() {
            return this.cityName;
        }

        public String getDefaultCityName() {
            return this.defaultCityName;
        }

        public String getState() {
            return this.state;
        }

        public String getZIPCode() {
            return this.ZIPCode;
        }

        public String getPlus4Code() {
            return this.plus4Code;
        }

        public String getDeliveryPoint() {
            return this.deliveryPoint;
        }

        public String getDeliveryPointCheckDigit() {
            return this.deliveryPointCheckDigit;
        }
    }

    private class Metadata {
        private String recordType;
        private String ZIPType;
        private String countyFIPS;
        private String countyName;
        private String carrierRoute;
        private String congressionalDistrict;
        private String buildingDefaultIndicator;
        private String RDI;
        private String ELOTSequence;
        private String ELOTSort;
        private double latitude;
        private double longitude;
        private String precision;
        private String timeZone;
        private double UTCOffset;
        private boolean usesDST;

        public String getRecordType() {
            return this.recordType;
        }

        public String getZIPType() {
            return this.ZIPType;
        }

        public String getCountyFIPS() {
            return this.countyFIPS;
        }

        public String getCountyName() {
            return this.countyName;
        }

        public String getCarrierRoute() {
            return this.carrierRoute;
        }

        public String getCongressionalDistrict() {
            return this.congressionalDistrict;
        }

        public String getBuildingDefaultIndicator() {
            return this.buildingDefaultIndicator;
        }

        public String getRDI() {
            return this.RDI;
        }

        public String getELOTSequence() {
            return this.ELOTSequence;
        }

        public String getELOTSort() {
            return this.ELOTSort;
        }

        public double getLatitude() {
            return this.latitude;
        }

        public double getLongitude() {
            return this.longitude;
        }

        public String getPrecision() {
            return this.precision;
        }

        public String getTimeZone() {
            return this.timeZone;
        }

        public double getUTCOffset() {
            return this.UTCOffset;
        }

        public boolean usesDST() {
            return this.usesDST;
        }
    }

    private class Analysis{
        private String DPVMatchCode;
        private String DPVFootnotes;
        private String CMRA;
        private String vacant;
        private String active;
        private boolean isEWSMatch;
        private String footnotes;
        private String LACSLinkCode;
        private String LACSLinkIndicator;
        private boolean isSuiteLinkMatch;

        public String getDPVMatchCode() {
            return this.DPVMatchCode;
        }

        public String getDPVFootnotes() {
            return this.DPVFootnotes;
        }

        public String getCMRA() {
            return this.CMRA;
        }

        public String getVacant() {
            return this.vacant;
        }

        public String getActive() {
            return this.active;
        }

        public boolean isEWSMatch() {
            return this.isEWSMatch;
        }

        public String getFootnotes() {
            return this.footnotes;
        }

        public String getLACSLinkCode() {
            return this.LACSLinkCode;
        }

        public String getLACSLinkIndicator() {
            return this.LACSLinkIndicator;
        }

        public boolean isSuiteLinkMatch() {
            return this.isSuiteLinkMatch;
        }
    }


    /**********************************************************************************************
     * Getters
     **********************************************************************************************/

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

    // Analysis -----------------------------------------------------------------------------------

    public boolean isValid() {
        String value = this.analysis.getDPVMatchCode();
        return (value != null && (value.equals("Y") || value.equals("S") || value.equals("D")));
    }

    public boolean CMRA() {
        String value = this.analysis.getCMRA();
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

}
