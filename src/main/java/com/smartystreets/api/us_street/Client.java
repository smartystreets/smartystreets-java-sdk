package com.smartystreets.api.us_street;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.*;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.smartystreets.api.*;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.io.StringWriter;

public class Client {
    private Credentials signer;
    private Sender inner;
    //private HttpTransport transport;

    public Client (Credentials signer, Sender inner) {
        this.signer = signer;
        this.inner = inner;
        //this.transport = new NetHttpTransport();
    }

    // Wraps address in a batch and calls the other send method
    public void send(AddressLookup lookup) throws SmartyException, IOException {
        Batch batch = new Batch();
        batch.add(lookup);
        this.send(batch);
    }

    // Sends lookup to the US street API
    public void send(Batch batch) throws SmartyException, IOException {
        Request request = new Request("https://api.smartystreets.com/street-address?");

        if (batch.size() == 0)
            return;

        // Add credentials to query
        this.signer.sign(request);

        // Determine if it is a single address or not, set method and serialize
        if (batch.size() == 1) {
            request.setMethod("GET");

            GoogleInterpreter.serializeIntoRequestUrl(batch, request);

           // request.setInnerRequest(factory.buildGetRequest(new GenericUrl(request.getUrlString())));
        } else {
            request.setMethod("POST");


            GoogleInterpreter.serializeIntoRequestBody(batch, request);

           /* HttpRequest innerRequest = factory.buildPostRequest(new GenericUrl(baseUrl),
                    new JsonHttpContent(new JacksonFactory(), batch.getAllLookups()));
            innerRequest.getHeaders().setContentType(Json.MEDIA_TYPE);

            request.setInnerRequest(innerRequest);*/
        }

        this.copyHeaders(batch, request);

        // Send request to API, and interpret the response
        Response response = this.inner.send(request); // can throw exceptions
        GoogleInterpreter.deserializeResponse(batch, response.getInnerResponse());
    }

    private void copyHeaders(Batch batch, Request request) {
        if (batch.getIncludeInvalid())
            request.addHeader("X-Include-Invalid", "true");
        else if (batch.getStandardizeOnly())
            request.addHeader("X-Standardize-Only", "true");
    }
}
