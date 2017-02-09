package com.smartystreets.api;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private static final String CHARSET = "UTF-8";
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private String urlPrefix;
    private String method;
    private String contentType;
    private byte[] payload;

    public Request() {
        this.urlPrefix = "";
        this.method = "GET";
        this.headers = new HashMap<>();
        this.parameters = new LinkedHashMap<>();
        this.contentType = "application/json";
    }

    public void putHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void putParameter(String name, String value) {
        if (name == null || value == null || name.length() == 0)
            return;

        parameters.put(name, value);
    }
    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, CHARSET);
        } catch (Exception ex) {
            return "";
        }
    }

    //region [ Getters ]

    public String getUrl() {
        String url = this.urlPrefix;

        if (!url.contains("?"))
            url += "?";

        for (String value : parameters.keySet()) {
            if (!url.endsWith("?"))
                url += "&";

            String encodedName = urlEncode(value);
            String encodedValue = urlEncode(parameters.get(value));
            url += encodedName + "=" + encodedValue;
        }

        return url;
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

    public String getContentType() {
        return contentType;
    }

    //endregion

    //region [ Setters ]

    public void setPayload(byte[] payload) {
        this.method = "POST";
        this.payload = payload;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    //endregion
}
