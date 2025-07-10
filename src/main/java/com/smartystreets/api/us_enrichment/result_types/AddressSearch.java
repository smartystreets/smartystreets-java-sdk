package com.smartystreets.api.us_enrichment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AddressSearch {
    private String freeform;
    private String street;
    private String city;
    private String state;
    private String zipcode;

    public String freeform() {
        return freeform;
    }

    public AddressSearch withFreeform(String freeform) {
        this.freeform = freeform;
        return this;
    }

    public String street() {
        return street;
    }

    public AddressSearch withStreet(String street) {
        this.street = street;
        return this;
    }

    public String city() {
        return this.city;
    }

    public AddressSearch withCity(String city) {
        this.city = city;
        return this;
    }

    public String state() {
        return this.state;
    }

    public AddressSearch withState(String state) {
        this.state = state;
        return this;
    }

    public String zipcode() {
        return this.zipcode;
    }

    public AddressSearch withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public String toSearchString() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if (this.freeform == null || this.freeform.isEmpty()) {
            sb.append("?street=");
            sb.append(this.encode(this.street));
            sb.append("&city=");
            sb.append(this.encode(this.city));
            sb.append("&state=");
            sb.append(this.encode(this.state));
            sb.append("&zipcode=");
            sb.append(this.encode(this.zipcode));
        } else {
            sb.append("?freeform=");
            sb.append(this.encode(this.freeform));
        }
        return sb.toString();
    }

    private String encode(String part) throws UnsupportedEncodingException {
        return URLEncoder.encode(part, StandardCharsets.UTF_8.toString());
    }
}
