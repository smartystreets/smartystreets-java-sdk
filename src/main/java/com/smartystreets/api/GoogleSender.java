package com.smartystreets.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.Json;
import com.smartystreets.api.exceptions.*;
import com.sun.javafx.tools.packager.bundlers.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class GoogleSender implements Sender {
    private final int BUFFER_SIZE = 16384;
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

    GoogleSender(HttpTransport transport) {
        this();
        this.transport = transport;
    }

    public Response send(Request request) throws SmartyException, IOException {
        HttpRequest httpRequest = buildHttpRequest(request);
        copyHeaders(request, httpRequest);

        try {
            return buildResponse(httpRequest.execute());
        } catch (HttpResponseException ex) {
            return new Response(ex.getStatusCode(), new byte[0]);
        }
    }

    private HttpRequest buildHttpRequest(Request request) throws IOException {
        HttpRequestFactory factory = this.transport.createRequestFactory();
        GenericUrl url = new GenericUrl(request.getUrl());

        if (request.getMethod().equals("GET"))
            return factory.buildGetRequest(url);

        ByteArrayContent content = new ByteArrayContent(Json.MEDIA_TYPE, request.getPayload());
        return factory.buildPostRequest(url, content);
    }

    private void copyHeaders(Request request, HttpRequest httpRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpRequest.setHeaders(httpHeaders);

        Map<String, String> headers = request.getHeaders();
        for (String headerName : headers.keySet())
            httpHeaders.set(headerName, headers.get(headerName));
    }

    private Response buildResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusCode();
        byte[] payload = readResponseBody(httpResponse);
        return new Response(statusCode, payload);
    }

    private byte[] readResponseBody(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int totalBytesRead = 0;
        byte[] buffer = new byte[BUFFER_SIZE];

        while ((totalBytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
            outputStream.write(buffer, 0, totalBytesRead);
        }

        return outputStream.toByteArray();
    }
}
