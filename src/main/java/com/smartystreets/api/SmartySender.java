package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class SmartySender implements Sender {
    private int maxTimeOut;
    private HttpClient client;

    public SmartySender() {
        this.maxTimeOut = 10000;
        this.client = HttpClient.newHttpClient();
    }

    public SmartySender(int maxTimeout) {
        this();
        this.maxTimeOut = maxTimeout;
    }

    SmartySender(int maxTimeOut, ProxySelector proxy) {
        this.maxTimeOut = maxTimeOut;
        this.client = HttpClient.newBuilder().proxy(proxy).build();
    }

    SmartySender(HttpClient client) {
        this();
        this.client = client;
    }

    public Response send(Request smartyRequest) throws SmartyException, IOException {
        HttpRequest httpRequest = buildHttpRequest(smartyRequest);

        try {
            return buildResponse(this.client.send(httpRequest, HttpResponse.BodyHandlers.ofString()));
        } catch(InterruptedException ex) {
            return new Response(ex.hashCode(), new byte[0]);
        }
    }

    private HttpRequest buildHttpRequest(Request smartyRequest) throws IOException {
        java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest
                .newBuilder(URI.create(smartyRequest.getUrl()))
                .timeout(Duration.ofSeconds(maxTimeOut));
        Map<String, Object> headers = smartyRequest.getHeaders();
        for (String headerName : headers.keySet())
            builder.setHeader(headerName, headers.get(headerName).toString());

        if (smartyRequest.getMethod().equals("GET"))
            return builder.GET().build();

        return builder
                .POST(HttpRequest.BodyPublishers.ofByteArray(smartyRequest.getPayload()))
                .build();
    }

    private Response buildResponse(HttpResponse httpResponse) {
        int statusCode = httpResponse.statusCode();
        if (statusCode == 429){
            return new TooManyRequestsResponse(httpResponse.headers(), statusCode, httpResponse.body().toString().getBytes());
        }
        return new Response(statusCode, httpResponse.body().toString().getBytes());
    }

    static void enableLogging() {
        Logger logger = Logger.getLogger(HttpClient.class.getName());
        logger.setLevel(Level.ALL);
        logger.addHandler(new Handler() {
            @Override
            public void close() throws SecurityException {
            }

            @Override
            public void flush() {
            }

            @Override
            public void publish (LogRecord record) {
                // default ConsoleHandler will print >= INFO to System.err
                if (record.getLevel().intValue() < Level.INFO.intValue())
                    System.out.println(record.getMessage());
            }
        });
    }
}
