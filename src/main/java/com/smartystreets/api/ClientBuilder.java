package com.smartystreets.api;


public class ClientBuilder {
    private Credentials signer;
    private Serializer serializer;
    private Sender httpSender;
    private int maxRetries;
    private int maxTimeout;
    private String urlPrefix;
    private final String INTERNATIONAL_STREET_API_URL = "https://international-street.api.smartystreets.com/verify";
    private final String US_AUTOCOMPLETE_API_URL = "https://us-autocomplete.api.smartystreets.com/suggest";
    private final String US_EXTRACT_API_URL = "https://us-extract.api.smartystreets.com";
    private final String US_STREET_API_URL = "https://us-street.api.smartystreets.com/street-address";
    private final String US_ZIP_CODE_API_URL = "https://us-zipcode.api.smartystreets.com/lookup";

    public ClientBuilder() {
        this.serializer = new GoogleSerializer();
        this.maxRetries = 5;
        this.maxTimeout = 10000;
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

    public ClientBuilder withCustomBaseUrl(String urlPrefix) {
        this.urlPrefix = urlPrefix;
        return this;
    }

    public com.smartystreets.api.international_street.Client buildInternationalStreetApiClient() {
        this.ensureURLPrefixNotNull(this.INTERNATIONAL_STREET_API_URL);
        return new com.smartystreets.api.international_street.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_autocomplete.Client buildUsAutocompleteApiClient() {
        this.ensureURLPrefixNotNull(this.US_AUTOCOMPLETE_API_URL);
        return new com.smartystreets.api.us_autocomplete.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_extract.Client buildUsExtractApiClient() {
        this.ensureURLPrefixNotNull(this.US_EXTRACT_API_URL);
        return new com.smartystreets.api.us_extract.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_street.Client buildUsStreetApiClient() {
        this.ensureURLPrefixNotNull(this.US_STREET_API_URL);
        return new com.smartystreets.api.us_street.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_zipcode.Client buildUsZipCodeApiClient() {
        this.ensureURLPrefixNotNull(this.US_ZIP_CODE_API_URL);
        return new com.smartystreets.api.us_zipcode.Client(this.buildSender(), this.serializer);
    }

    public Sender buildSender() {
        if (this.httpSender != null)
            return this.httpSender;

        Sender sender = new GoogleSender(this.maxTimeout);

        sender = new StatusCodeSender(sender);

        if (this.signer != null)
            sender = new SigningSender(this.signer, sender);

        sender = new URLPrefixSender(this.urlPrefix, sender);

        if (this.maxRetries > 0)
            sender = new RetrySender(this.maxRetries, new MySleeper(), new MyLogger(), sender);

        return sender;
    }

    private void ensureURLPrefixNotNull(String url) {
        if (this.urlPrefix == null)
            this.urlPrefix = url;
    }
}
