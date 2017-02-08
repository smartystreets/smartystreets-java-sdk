package com.smartystreets.api.us_extract;


import com.google.api.client.util.Key;

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
