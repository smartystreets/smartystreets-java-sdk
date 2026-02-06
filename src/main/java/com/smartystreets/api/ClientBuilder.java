package com.smartystreets.api;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ClientBuilder class helps you build a client object for one of the
 * supported SmartyStreets APIs.<br>
 * You can use ClientBuilder's methods to customize settings like maximum
 * retries or timeout duration. These methods<br>
 * are chainable, so you can usually get set up with one line of code.
 */
public class ClientBuilder {
    private final static String INTERNATIONAL_STREET_API_URL = "https://international-street.api.smarty.com/verify";
    private final static String INTERNATIONAL_AUTOCOMPLETE_API_URL = "https://international-autocomplete.api.smarty.com/v2/lookup";
    private final static String US_AUTOCOMPLETE_API_PRO_URL = "https://us-autocomplete-pro.api.smarty.com/lookup";
    private final static String US_EXTRACT_API_URL = "https://us-extract.api.smarty.com/";
    private final static String US_STREET_API_URL = "https://us-street.api.smarty.com/street-address";
    private final static String US_ZIP_CODE_API_URL = "https://us-zipcode.api.smarty.com/lookup";
    private final static String US_REVERSE_GEO_API_URL = "https://us-reverse-geo.api.smarty.com/lookup";
    private static final String US_ENRICHMENT_API_URL = "https://us-enrichment.api.smarty.com/lookup";
    private final static String INTERNATIONAL_POSTAL_CODE_API_URL = "https://international-postal-code.api.smarty.com/lookup";
    private Credentials signer;
    private Serializer serializer;
    private Sender httpSender;
    private int maxRetries;
    private int maxTimeout;
    private String urlPrefix;
    private Proxy proxy;
    private Map<String, CustomHeader> customHeaders;
    private List<String> licenses = new ArrayList<>();
    private Map<String, String> customQueries;
    private String ip;

    private ClientBuilder() {
        this.serializer = new SmartySerializer();
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

    /**
     * @param maxRetries The maximum number of times to retry sending the request to
     *                   the API. (Default is 5)
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder retryAtMost(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    /**
     * @param maxTimeout The maximum time (in milliseconds) to wait for a
     *                   connection, and also to wait for <br>
     *                   the response to be read. (Default is 10000)
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withMaxTimeout(int maxTimeout) {
        this.maxTimeout = maxTimeout;
        return this;
    }

    /**
     * @param sender Default is a series of nested senders. See
     *               <b>buildSender()</b>.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withSender(Sender sender) {
        this.httpSender = sender;
        return this;
    }

    /**
     * Changes the <b>Serializer</b> from the default <b>SmartySerializer</b>.
     * 
     * @param serializer An object that implements the <b>Serializer</b> interface.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withSerializer(Serializer serializer) {
        this.serializer = serializer;
        return this;
    }

    /**
     * This may be useful when using a local installation of the SmartyStreets APIs.
     * 
     * @param baseUrl Defaults to the URL for the API corresponding to the
     *                <b>Client</b> object being built.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withCustomBaseUrl(String baseUrl) {
        this.urlPrefix = baseUrl;
        return this;
    }

    /**
     * Use this to add any additional headers you need.
     * 
     * @param customHeaders A String to Object <b>Map</b> of header name/value
     *                      pairs.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withCustomHeaders(Map<String, Object> customHeaders) {
        if (this.customHeaders == null) {
            this.customHeaders = new HashMap<>();
        }
        for (Map.Entry<String, Object> entry : customHeaders.entrySet()) {
            this.customHeaders.put(entry.getKey(), new CustomHeader(entry.getValue()));
        }
        return this;
    }

    /**
     * Appends the provided value to the existing header value using the specified separator,
     * rather than adding a separate header value. This is useful for single-value headers like User-Agent.
     *
     * @param key       The header name.
     * @param value     The value to append.
     * @param separator The separator to use when joining values.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withAppendedHeader(String key, String value, String separator) {
        if (this.customHeaders == null) {
            this.customHeaders = new HashMap<>();
        }
        this.customHeaders.put(key, new CustomHeader(value, separator));
        return this;
    }

    /**
     * Use this to specify a proxy through which to send all lookups.
     * 
     * @param proxyType Choose a java.net.Proxy.Type.
     * @param proxyHost The host of the proxy server (do not include the port).
     * @param proxyPort The port on the proxy server to which you wish to connect.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withProxy(Proxy.Type proxyType, String proxyHost, int proxyPort) {
        this.proxy = new Proxy(proxyType, new InetSocketAddress(proxyHost, proxyPort));
        return this;
    }

    public ClientBuilder withXForwardedFor(String ip) {
        this.ip = ip;
        return this;
    }

    /**
     * Enables debug mode, which will print information about the HTTP request and
     * response to the console.
     * 
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withDebug() {
        SmartySender.enableLogging();
        return this;
    }

    /**
     * Allows caller to specify licenses (aka "tracks") they wish to use.
     * 
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withLicenses(List<String> licenses) {
        this.licenses.addAll(licenses);
        return this;
    }

    /**
     * Allows the caller to specify key and value pair that is added to the rerquest
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withCustomQuery(String key, String value) {
        if (this.customQueries == null) {
            this.customQueries = new HashMap<>();
        }
        this.customQueries.put(key, value);
        return this;
    }

    /**
     * Allows the caller to specify key and value pair and appends the value to the current value associated with the key, separated by a comma.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withCustomCommaSeparatedQuery(String key, String value) {
        if (this.customQueries == null) {
            this.customQueries = new HashMap<>();
        }
        this.customQueries.put(key, this.customQueries.getOrDefault(key, "") + (this.customQueries.containsKey(key) ? "," : "") + value);
        return this;
    }

    /**
     * Adds to the request query to use the component analysis feature.
     * @return Returns <b>this</b> to accommodate method chaining.
     */
    public ClientBuilder withFeatureComponentAnalysis() {
        return withCustomCommaSeparatedQuery("features", "component-analysis");
    }


    public com.smartystreets.api.international_street.Client buildInternationalStreetApiClient() {
        this.ensureURLPrefixNotNull(INTERNATIONAL_STREET_API_URL);
        return new com.smartystreets.api.international_street.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.international_autocomplete.Client buildInternationalAutcompleteApiClient() {
        this.ensureURLPrefixNotNull(INTERNATIONAL_AUTOCOMPLETE_API_URL);
        return new com.smartystreets.api.international_autocomplete.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_autocomplete_pro.Client buildUsAutocompleteProApiClient() {
        this.ensureURLPrefixNotNull(US_AUTOCOMPLETE_API_PRO_URL);
        return new com.smartystreets.api.us_autocomplete_pro.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_extract.Client buildUsExtractApiClient() {
        this.ensureURLPrefixNotNull(US_EXTRACT_API_URL);
        return new com.smartystreets.api.us_extract.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_street.Client buildUsStreetApiClient() {
        this.ensureURLPrefixNotNull(US_STREET_API_URL);
        return new com.smartystreets.api.us_street.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_zipcode.Client buildUsZipCodeApiClient() {
        this.ensureURLPrefixNotNull(US_ZIP_CODE_API_URL);
        return new com.smartystreets.api.us_zipcode.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_reverse_geo.Client buildUsReverseGeoClient() {
        this.ensureURLPrefixNotNull(US_REVERSE_GEO_API_URL);
        return new com.smartystreets.api.us_reverse_geo.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.us_enrichment.Client buildUsEnrichmentClient() {
        this.ensureURLPrefixNotNull(US_ENRICHMENT_API_URL);
        return new com.smartystreets.api.us_enrichment.Client(this.buildSender(), this.serializer);
    }

    public com.smartystreets.api.international_postal_code.Client buildInternationalPostalCodeApiClient() {
        this.ensureURLPrefixNotNull(INTERNATIONAL_POSTAL_CODE_API_URL);
        return new com.smartystreets.api.international_postal_code.Client(this.buildSender(), this.serializer);
    }

    private Sender buildSender() {
        if (this.httpSender != null)
            return this.httpSender;

        Sender sender;
        if (this.proxy != null) {
            sender = new SmartySender(this.maxTimeout, this.proxy);
        } else {
            sender = new SmartySender(this.maxTimeout);
        }

        sender = new StatusCodeSender(sender);
        if (this.ip != null) {
            if (this.customHeaders == null) {
                this.customHeaders = new HashMap<>();
            }
            customHeaders.put("X-Forwarded-For", new CustomHeader(this.ip));
        }
        if (this.customHeaders != null) {
            sender = new CustomHeaderSender(this.customHeaders, sender);
        }
        if (this.customQueries != null) {
            sender = new CustomQuerySender(this.customQueries, sender);
        }
        if (this.signer != null) {
            sender = new SigningSender(this.signer, sender);
        }
        sender = new URLPrefixSender(this.urlPrefix, sender);

        if (this.maxRetries > 0) {
            sender = new RetrySender(this.maxRetries, new MySleeper(), new MyLogger(), sender);
        }

        sender = new LicenseSender(this.licenses, sender);

        return sender;
    }

    private void ensureURLPrefixNotNull(String url) {
        if (this.urlPrefix == null)
            this.urlPrefix = url;
    }
}
