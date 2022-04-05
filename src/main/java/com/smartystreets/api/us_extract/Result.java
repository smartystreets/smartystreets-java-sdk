package com.smartystreets.api.us_extract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @see <a href="https://smartystreets.com/docs/cloud/us-extract-api#http-response-status">SmartyStreets US Extract API docs</a>
 */
public class Result implements Serializable {
    private Metadata metadata;
    private Address[] addresses;


    @JsonProperty("meta")
    public Metadata getMetadata() {
        return metadata;
    }

    public Address[] getAddresses() {
        return addresses;
    }

    public Address getAddress(int index) {
        return addresses[index];
    }
}
