package com.smartystreets.api.us_extract;


public class Lookup {
    //region [ Fields ]

    private Result result;
    private boolean html;
    private boolean aggressive;
    private boolean addressesHaveLineBreaks;
    private int addressesPerLine;
    private String text;

    //endregion

    public Lookup() {
        this.result = new Result();
    }

    public Lookup(String text) {
        this();
        this.text = text;
    }

    //region [ Getters ]

    public Result getResult() {
        return result;
    }

    public boolean isHtml() {
        return html;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public boolean AddressesHaveLineBreaks() {
        return addressesHaveLineBreaks;
    }

    public int getAddressesPerLine() {
        return addressesPerLine;
    }

    public String getText() {
        return text;
    }

    //endregion

    //region [ Setters ]

    public void setResult(Result result) {
        this.result = result;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }

    public void setAddressesHaveLineBreaks(boolean addressesHaveLineBreaks) {
        this.addressesHaveLineBreaks = addressesHaveLineBreaks;
    }

    public void setAddressesPerLine(int addressesPerLine) {
        this.addressesPerLine = addressesPerLine;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    //endregion
}
