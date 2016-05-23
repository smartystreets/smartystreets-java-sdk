package com.smartystreets.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.smartystreets.api.exceptions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoogleSender implements Sender {
    private int maxTimeOut;
    private HttpTransport httpTransport;

    public GoogleSender() {
        this.maxTimeOut = 10000;
        this.httpTransport = new NetHttpTransport();
    }

    public GoogleSender(int maxTimeout) {
        this();
        this.maxTimeOut = maxTimeout;
    }

    public void setHttpTransport(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    public Response send(Request request) throws SmartyException, IOException {
        try {
            //region [ Request ]
            Map<String, String> headers = request.getHeaders();
            HttpHeaders httpHeaders = new HttpHeaders();
            for (String headerName : headers.keySet()) {
                httpHeaders.set(headerName, headers.get(headerName));
            }

            HttpRequestFactory factory = this.httpTransport.createRequestFactory();

            HttpRequest httpRequest;

            if (request.getMethod().equals("GET")) {
                httpRequest = factory.buildGetRequest(new GenericUrl(request.getUrl()));
            } else { //POST
                httpRequest = factory.buildPostRequest(new GenericUrl(request.getUrl()), new JsonHttpContent(new JacksonFactory(), request.getPayload()));
            }

            httpRequest.setHeaders(httpHeaders);

            //endregion

            HttpResponse httpResponse = httpRequest.execute();

            //region [ Response ]

            int statusCode = httpResponse.getStatusCode();
            InputStream inputStream = httpResponse.getContent();
            byte[] payload = new byte[inputStream.available()];
            inputStream.read(payload);

            //endregion

            return new Response(statusCode, payload);
        } catch (HttpResponseException ex) {
            return new Response(ex.getStatusCode(), new byte[0]);
        }
    }

    public int getMaxTimeOut() {
        return this.maxTimeOut;
    }
}
