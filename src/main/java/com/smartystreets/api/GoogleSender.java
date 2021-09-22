package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class GoogleSender implements Sender {
    private int maxTimeOut;
    private HttpTransport transport;

    public GoogleSender() {
        this.maxTimeOut = 10000;
        this.transport = new NetHttpTransport();
    }

    public GoogleSender(int maxTimeout) {
        this();
        this.maxTimeOut = maxTimeout;
    }

    GoogleSender(int maxTimeOut, Proxy proxy) {
        this.maxTimeOut = maxTimeOut;
        this.transport = new NetHttpTransport.Builder().setProxy(proxy).build();
    }

    GoogleSender(HttpTransport transport) {
        this();
        this.transport = transport;
    }

    public Response send(Request smartyRequest) throws SmartyException, IOException {
        HttpRequest httpRequest = buildHttpRequest(smartyRequest);
        httpRequest.setConnectTimeout(this.maxTimeOut);
        httpRequest.setReadTimeout(this.maxTimeOut);
        this.copyHeaders(smartyRequest, httpRequest);

        try {
            return buildResponse(httpRequest.execute());
        } catch (HttpResponseException ex) {
            return new Response(ex.getStatusCode(), new byte[0]);
        }
    }

    private HttpRequest buildHttpRequest(Request smartyRequest) throws IOException {
        java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest.newBuilder(URI.create(smartyRequest.getUrl()));
        Map<String, Object> headers = smartyRequest.getHeaders();
        for (String headerName : headers.keySet())
            builder.setHeader(headerName, headers.get(headerName).toString());

        if (smartyRequest.getMethod().equals("GET"))
            return builder.GET().build();


        return builder
                .POST(smartyRequest.getPayload())




        HttpRequestFactory factory = this.transport.createRequestFactory();
        GenericUrl url = new GenericUrl(smartyRequest.getUrl());

        if (smartyRequest.getMethod().equals("GET"))
            return factory.buildGetRequest(url);

        ByteArrayContent content = new ByteArrayContent(smartyRequest.getContentType(), smartyRequest.getPayload());
        return factory.buildPostRequest(url, content);
    }

    private void copyHeaders(Request smartyRequest, HttpRequest httpRequest) {
        HttpHeaders httpHeaders = httpRequest.getHeaders();

        Map<String, Object> headers = smartyRequest.getHeaders();
        for (String headerName : headers.keySet())
            httpHeaders.set(headerName, headers.get(headerName));

        httpHeaders.setUserAgent("smartystreets (sdk:java@" + Version.CURRENT + ")");
    }

    private Response buildResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusCode();
        byte[] payload = readResponseBody(httpResponse);
        return new Response(statusCode, payload);
    }

    private byte[] readResponseBody(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int totalBytesRead;
        final int BUFFER_SIZE = 16384;
        byte[] buffer = new byte[BUFFER_SIZE];

        while ((totalBytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
            outputStream.write(buffer, 0, totalBytesRead);
        }

        return outputStream.toByteArray();
    }

    static void enableLogging() {
        Logger logger = Logger.getLogger(HttpTransport.class.getName());
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
