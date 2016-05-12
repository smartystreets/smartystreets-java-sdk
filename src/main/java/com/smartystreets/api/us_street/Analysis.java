package com.smartystreets.api.us_street;

import com.google.api.client.util.Key;

public class Analysis {
    //region [ Fields ]

    @Key("dpv_match_code")
    private String DPVMatchCode; //TODO field in-casing

    @Key("dpv_footnotes")
    private String DPVFootnotes;

    @Key("dpv_cmra")
    private String CMRA;

    @Key("dpv_vacant")
    private String vacant;

    @Key("active")
    private String active;

    @Key("ews_match")
    private boolean isEWSMatch;

    @Key("footnotes")
    private String footnotes;

    @Key("lacslink_code")
    private String LACSLinkCode;

    @Key("lacslink_indicator")
    private String LACSLinkIndicator;

    @Key("suitelink_match")
    private boolean isSuiteLinkMatch;

    //endregion

    //region [ Getters ]

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

    //endregion
}
