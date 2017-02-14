package com.smartystreets.api.us_extract;

/**
 * In addition to holding all of the input data for this lookup, this class also<br>
 *     will contain the result of the lookup after it comes back from the API.
 *     @see "https://smartystreets.com/docs/cloud/us-extract-api#http-request-input-fields"
 */
public class Lookup {
    //region [ Fields ]

    private Result result;
    private String html;
    private boolean aggressive;
    private boolean addressesHaveLineBreaks;
    private int addressesPerLine;
    private String text;

    //endregion

    public Lookup() {
        this.result = new Result();
        this.addressesHaveLineBreaks = true;
    }

    /**
     * @param text The text that is to have addresses extracted out of it for verification
     */
    public Lookup(String text) {
        this();
        this.text = text;
    }

    //region [ Getters ]

    public Result getResult() {
        return result;
    }

    public String isHtml() {
        return html;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public boolean addressesHaveLineBreaks() {
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

    public void isHtml(boolean html) {
        this.html = String.valueOf(html);
    }

    public void isAggressive(boolean aggressive) {
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
