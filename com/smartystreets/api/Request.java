package com.smartystreets.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neo on 4/25/16.
 */
public class Request {

    private String urlString;
    private String method;
    private Map<String, String> headers;
    private String jsonPayload;

    public Request(){
        this.headers = new HashMap<>();
    }

    public Request(String urlString) {
        this.headers = new HashMap<>();
        this.urlString = urlString;
    }

    public void addHeader(String name, String value){
        this.headers.put(name, value);
    }

    /**** Getters ********************************************************************************/

    public String getUrlString() {
        return urlString;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }

    /**** Setters ********************************************************************************/

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }
}
