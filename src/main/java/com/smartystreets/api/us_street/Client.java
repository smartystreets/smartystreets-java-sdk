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
    private HttpTransport transport;

    public Client (Credentials signer, Sender inner) {
        this.signer = signer;
        this.inner = inner;
        this.transport = new NetHttpTransport();
    }

    // Wraps address in a batch and calls the other send method
    public void send(AddressLookup lookup) throws SmartyException, IOException {
        Batch batch = new Batch();
        batch.add(lookup);
        this.send(batch);
    }

    // Sends lookup to the US street API
    public void send(Batch batch) throws SmartyException, IOException {
        HttpRequestFactory factory = this.transport.createRequestFactory();
        String baseUrl = "https://api.smartystreets.com/street-address?";
        Request request = new Request(baseUrl);

        // Determine if it is a single address or not, set method and serialize
        if (batch.size() == 0)
            return;
        if (batch.size() == 1) {
            request.setMethod("GET");

            // Add credentials to url
            this.signer.sign(request);
            this.serializeIntoRequestUrl(batch, request);

            request.setInnerRequest(factory.buildGetRequest(new GenericUrl(request.getUrlString())));
        } else {
            request.setMethod("POST");

            this.signer.sign(request);
            this.serializeIntoRequestBody(batch, request);

            HttpRequest innerRequest = factory.buildPostRequest(new GenericUrl(baseUrl),
                    new JsonHttpContent(new JacksonFactory(), batch.getAllLookups()));
            innerRequest.getHeaders().setContentType(com.google.api.client.json.Json.MEDIA_TYPE);

            request.setInnerRequest(innerRequest);
        }

        this.copyHeaders(batch, request);

        // Send request to API, and interpret the response
        Response response = this.inner.send(request); // can throw exceptions
        this.deserializeResponse(batch, response.getInnerResponse());
    }

    static void serializeIntoRequestUrl(Batch batch, Request request) {
        AddressLookup address = batch.get(0);

        request.appendParameter("input_id", address.getInputId());
        request.appendParameter("input_id", address.getInputId());
        request.appendParameter("street", address.getStreet());
        request.appendParameter("street2",address.getStreet2());
        request.appendParameter("secondary", address.getSecondary());
        request.appendParameter("city", address.getCity());
        request.appendParameter("state", address.getState());
        request.appendParameter("zipcode", address.getZipcode());
        request.appendParameter("lastline", address.getLastline());
        request.appendParameter("addressee", address.getAddressee());
        request.appendParameter("urbanization", address.getUrbanization());

        if (address.getMaxCandidates() != 1)
            request.appendParameter("candidates", Integer.toString(address.getMaxCandidates()));
    }

    //TODO consider getting rid of serializeIntoRequestBody() on both us_street and us_zipcode
    static void serializeIntoRequestBody(Batch batch, Request request) throws IOException {
        JacksonFactory jacksonFactory = new JacksonFactory();
        StringWriter jsonWriter = new StringWriter();
        JsonGenerator generator = jacksonFactory.createJsonGenerator(jsonWriter);

        generator.serialize(batch.getAllLookups());
        generator.close();

        request.setJsonPayload(jsonWriter.toString());
    }

    // Loads the raw JSON response into Candidate objects, and puts those into the appropriate AddressLookups
    static void deserializeResponse(Batch batch, HttpResponse response) throws IOException {
        Candidate[] candidates = response.parseAs(Candidate[].class);

        for (int i = 0; i < batch.size(); i++) {
            for (int j = 0; j < candidates.length; j++) {
                if (candidates[j].getInputIndex() == i) {
                    batch.get(i).addToResult(candidates[j]);
                }
            }
        }
    }

    private void copyHeaders(Batch batch, Request request) {
        if (batch.getIncludeInvalid())
            request.addHeader("X-Include-Invalid", "true");
        else if (batch.getStandardizeOnly())
            request.addHeader("X-Standardize-Only", "true");
    }
}
