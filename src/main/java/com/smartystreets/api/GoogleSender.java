package com.smartystreets.api;

import com.google.api.client.http.*;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.smartystreets.api.exceptions.*;
import org.apache.http.*;
import org.omg.CORBA_2_3.portable.*;

import java.io.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GoogleSender implements Sender {
    private int maxTimeOut;

    public GoogleSender() {
        this.maxTimeOut = 10000;
    }

    public GoogleSender(int maxTimeout) {
        this.maxTimeOut = maxTimeout;
    }

    public Response send(Request request) throws SmartyException, IOException {
        try {
            //region [ Request ]
            Map<String, String> headers = request.getHeaders();
            HttpHeaders httpHeaders = new HttpHeaders();
            for (String headerName : headers.keySet()) {
                httpHeaders.set(headerName, headers.get(headerName));
            }

            HttpRequestFactory factory = new NetHttpTransport().createRequestFactory();

            HttpRequest httpRequest;

            if (request.getMethod().equals("GET")) {
                httpRequest = factory.buildGetRequest(new GenericUrl(request.getUrlString()));
            }
            else { //POST
                httpRequest = factory.buildPostRequest(new GenericUrl(request.getUrlString()), new JsonHttpContent(new JacksonFactory(), request.getPayload()));
            }

            httpRequest.setHeaders(httpHeaders);

            //endregion

            HttpResponse httpResponse = httpRequest.execute();

            //region [ Response ]

            HttpHeaders responseHttpHeaders = httpResponse.getHeaders();
            Map<String, String> responseHeaders = new HashMap<>();
            for (String headerName : httpHeaders.keySet()) {
                responseHeaders.put(headerName, (String) responseHttpHeaders.get(headerName));
            }
            int statusCode = httpResponse.getStatusCode();
            InputStream inputStream = httpResponse.getContent();
            byte[] payload = new byte[inputStream.available()];
            inputStream.read(payload);

            //endregion

            return new Response(statusCode, responseHeaders, payload);
        }
        catch(HttpResponseException ex) {
            switch (ex.getStatusCode()) {
                case 400: throw new BadRequestException("Bad Request (Malformed Payload): A GET request lacked a street field or the request body of a POST request contained malformed JSON.");
                case 401: throw new UnauthorizedException("Unauthorized: The credentials were provided incorrectly or did not match any existing, active credentials.");
                case 402: throw new PaymentRequiredException("Payment Required: There is no active subscription for the account associated with the credentials submitted with the request.");
                case 413: throw new RequestEntityTooLargeException("Request Entity Too Large: The maximum size for a request body to this API is 32K (32,768 bytes).");
                case 429: throw new TooManyRequestsException("Too Many Requests: When using public \"website key\" authentication, we restrict the number of requests coming from a given source over too short of a time.");
                default: throw ex;
            }
        }
    }

    public int getMaxTimeOut() {
        return this.maxTimeOut;
    }
}
