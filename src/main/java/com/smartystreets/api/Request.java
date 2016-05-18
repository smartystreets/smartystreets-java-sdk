package com.smartystreets.api;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private static final String CHARSET = "UTF-8";
    private final Map<String, String> headers;
    private String urlString;
    private String method;
    private byte[] payload;

    public Request() {
        this.method = "GET";
        this.headers = new HashMap<>();
    }
    public Request(String urlString) {
        this();
        this.urlString = urlString;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    //TODO: change appendParameter to putParameter (put on a map instead)
    public void appendParameter(String name, String value) {
        if (name == null || value == null)
            return;

        if (name.length() == 0)
            return;

        if (this.urlString.contains("?"))
            this.urlString += "&";
        else
            this.urlString += "?";

        String encodedName = urlEncode(name);
        String encodedValue = urlEncode(value);
        this.urlString += encodedName + "=" + encodedValue;
    }
    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, CHARSET);
        } catch (Exception ex) {
            return "";
        }
    }

    //region [ Getters ]

    public String getUrlString() {
        return urlString;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    //endregion
    //region [ Setters ]

    public void setPayload(byte[] payload) {
        this.method = "POST";
        this.payload = payload;
    }

    //endregion
}
