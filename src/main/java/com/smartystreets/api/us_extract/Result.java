package com.smartystreets.api.us_extract;


import com.google.api.client.util.Key;

/**
 * @see <a href="https://smartystreets.com/docs/cloud/us-extract-api#http-response-status">SmartyStreets US Extract API docs</a>
 */
public class Result {
    @Key("meta")
    private Metadata metadata;

    @Key("addresses")
    private Address[] addresses;


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
