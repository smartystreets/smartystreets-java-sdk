package com.smartystreets.api.us_street;

import com.google.api.client.util.Key;

/**
 * @see "https://smartystreets.com/docs/cloud/us-street-api#analysis"
 */
public class Analysis {
    //region [ Fields ]

    @Key("dpv_match_code")
    private String dpvMatchCode;

    @Key("dpv_footnotes")
    private String dpvFootnotes;

    @Key("dpv_cmra")
    private String cmra;

    @Key("dpv_vacant")
    private String vacant;

    @Key("dpv_no_stat")
    private String no_stat;

    @Key("active")
    private String active;

    @Key("ews_match")
    private boolean isEwsMatch;

    @Key("footnotes")
    private String footnotes;

    @Key("lacslink_code")
    private String lacsLinkCode;

    @Key("lacslink_indicator")
    private String lacsLinkIndicator;

    @Key("suitelink_match")
    private boolean isSuiteLinkMatch;

    //endregion

    //region [ Getters ]

    public String getDpvMatchCode() {
        return this.dpvMatchCode;
    }

    public String getDpvFootnotes() {
        return this.dpvFootnotes;
    }

    public String getCmra() {
        return this.cmra;
    }

    public String getVacant() {
        return this.vacant;
    }

    public String getNo_stat() { return this.no_stat; }

    public String getActive() {
        return this.active;
    }

    //@deprecated moved to metadata field
    @Deprecated
    public boolean isEwsMatch() {
        return false;
    }

    public String getFootnotes() {
        return this.footnotes;
    }

    public String getLacsLinkCode() {
        return this.lacsLinkCode;
    }

    public String getLacsLinkIndicator() {
        return this.lacsLinkIndicator;
    }

    public boolean isSuiteLinkMatch() {
        return this.isSuiteLinkMatch;
    }

    //endregion
}
