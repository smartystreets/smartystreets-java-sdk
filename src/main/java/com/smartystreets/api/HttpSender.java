package com.smartystreets.api;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.smartystreets.api.exceptions.*;

import java.io.*;
import java.util.Map;

public class HttpSender implements Sender {
    private int maxTimeOut;

    public HttpSender() {
        this.maxTimeOut = 10000;
    }

    public HttpSender(int maxTimeout) {
        this.maxTimeOut = maxTimeout;
    }

    public Response send(Request request) throws SmartyException, IOException{
        Response response = new Response();

        //TODO: @Overload HttpHeaders class? to call addHeader()

//        try {
            Map<String, String> headers = request.getHeaders();
            HttpRequest innerRequest = request.getInnerRequest();
            HttpHeaders httpHeaders = new HttpHeaders();
            for (String headerName : headers.keySet()) {
                httpHeaders.set(headerName, headers.get(headerName));
            }

            innerRequest.setHeaders(httpHeaders);

            HttpResponse innerResponse = innerRequest.execute();
            response.setInnerResponse(innerResponse);

 //       }
 //       catch(IOException ex) {
//            switch (connection.getResponseCode()) {
//                case 400: throw new BadRequestException("Bad Request (Malformed Payload): A GET request lacked a street field or the request body of a POST request contained malformed JSON.");
//                case 401: throw new UnauthorizedException("Unauthorized: The credentials were provided incorrectly or did not match any existing, active credentials.");
//                case 402: throw new PaymentRequiredException("Payment Required: There is no active subscription for the account associated with the credentials submitted with the request.");
//                case 413: throw new RequestEntityTooLargeException("Request Entity Too Large: The maximum size for a request body to this API is 32K (32,768 bytes).");
//                case 429: throw new TooManyRequestsException("Too Many Requests: When using public \"website key\" authentication, we restrict the number of requests coming from a given source over too short of a time.");
//                default: throw ex;
//            }
//        }

        return response;
    }
}
