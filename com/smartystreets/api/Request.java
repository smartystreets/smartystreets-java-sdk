package com.smartystreets.api;

import java.util.ArrayList;

/**
 * Created by Neo on 4/25/16.
 */
public class Request {

    private String urlString;
    private String method;
    private ArrayList<String> headers;
    private String jsonPayload;

    public Request(){}

    public Request(String urlString) {
        this.urlString = urlString;
    }

    public void addHeader(String header){
        this.headers.add(header);
    }

    /**** Getters ********************************************************************************/

    public String getUrlString() {
        return urlString;
    }

    public String getMethod() {
        return method;
    }

    public ArrayList<String> getHeaders() {
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

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }
}
