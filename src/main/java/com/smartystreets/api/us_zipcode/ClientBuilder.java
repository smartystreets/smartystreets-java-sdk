package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.*;

public class ClientBuilder {
    private Credentials signer;
    private Serializer serializer;
    private Sender httpSender;
    private int maxRetries;
    private int maxTimeout;
    private String url;

    ClientBuilder() {
        this.serializer = new GoogleSerializer();
        this.maxRetries = 5;
        this.maxTimeout = 10000;
        this.url = "https://us-zipcode.api.smartystreets.com/lookup?";
    }

    public ClientBuilder(Credentials signer) {
        this();
        this.signer = signer;
    }

    public ClientBuilder(String authId, String authToken) {
        this(new StaticCredentials(authId, authToken));
    }

    public ClientBuilder retryAtMost(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public ClientBuilder withMaxTimeout(int maxTimeout) {
        this.maxTimeout = maxTimeout;
        return this;
    }

    public ClientBuilder withSender(Sender sender) {
        this.httpSender = sender;
        return this;
    }

    public ClientBuilder withSerializer(Serializer serializer) {
        this.serializer = serializer;
        return this;
    }

    public ClientBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public Client build() {
        return new Client(this.url, this.buildSender(), this.serializer);
    }

    public Sender buildSender() {
        if (this.httpSender != null)
            return this.httpSender;

        Sender sender = new GoogleSender(this.maxTimeout);

        if (this.signer != null)
            sender = new SigningSender(this.signer, sender);

        if (this.maxRetries > 0)
            sender = new RetrySender(this.maxRetries, sender);

        return sender;
    }
}
