package com.smartystreets.api.us_street;


import com.smartystreets.api.*;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by Neo on 4/6/16.
 */


public class Client {
    private Credentials signer;
    private Sender inner;

    public Client (Credentials signer) {
        this.signer = signer;
        this.inner = new HttpSender();
    }

    // Wraps address in a batch and calls the other send method
    public void send(AddressLookup lookup) throws Exception{
        Batch batch = new Batch();
        batch.add(lookup);
        this.send(batch);
    }

    // Sends lookup to the US street API
    public void send(Batch batch) throws Exception{
        // New Request
        Request request = new Request("https://api.smartystreets.com/street-address?");

        // Determine if it is a single address or not, set method and serialize
        if (batch.size() == 0)
            return;
        if (batch.size() == 1) {
            request.setMethod("GET");
            // Add credentials to url
            this.signer.sign(request); // throws when signing a POST request using website key authentication credentials
            request.setUrlString(request.getUrlString() + this.serializeGET(batch));
        } else {
            request.setMethod("POST");
            // Add credentials to url
            this.signer.sign(request); // throws when signing a POST request using website key authentication credentials
            request.addHeader("Content-Type", "application/json");
            request.setJsonPayload(this.serializePOST(batch));
        }

        copyHeaders(batch, request);

        // Send request to API, and interpret the response
        Response response = this.inner.send(request); // can throw exceptions
        this.deserializeResponse(response.getRawJSON(), batch);
    }

    private String serializeGET(Batch batch) throws UnsupportedEncodingException {
        String serializedAddress = "";
        AddressLookup address = batch.get(0);

        if (address.getInputId() != null)
            serializedAddress += "&input_id=" + URLEncoder.encode(address.getInputId(), "UTF-8");
        if (address.getStreet() != null)
            serializedAddress += "&street=" + URLEncoder.encode(address.getStreet(), "UTF-8");
        if (address.getStreet2() != null)
            serializedAddress += "&street2=" + URLEncoder.encode(address.getStreet2(), "UTF-8");
        if (address.getSecondary() != null)
            serializedAddress += "&secondary=" + URLEncoder.encode(address.getSecondary(), "UTF-8");
        if (address.getCity() != null)
            serializedAddress += "&city=" + URLEncoder.encode(address.getCity(), "UTF-8");
        if (address.getState() != null)
            serializedAddress += "&state=" + URLEncoder.encode(address.getState(), "UTF-8");
        if (address.getZipcode() != null)
            serializedAddress += "&zipcode=" + URLEncoder.encode(address.getZipcode(), "UTF-8");
        if (address.getLastline() != null)
            serializedAddress += "&lastline=" + URLEncoder.encode(address.getLastline(), "UTF-8");
        if (address.getAddressee() != null)
            serializedAddress += "&addressee=" + URLEncoder.encode(address.getAddressee(), "UTF-8");
        if (address.getUrbanization() != null)
            serializedAddress += "&urbanization=" + URLEncoder.encode(address.getUrbanization(), "UTF-8");
        if (address.getMaxCandidates() != 1)
            serializedAddress += "&candidates=" + address.getMaxCandidates();

        return serializedAddress;
    }

    private String serializePOST(Batch batch) {
        String payload;
        StringWriter jsonWriter = new StringWriter();
        JsonGenerator generator = Json.createGenerator(jsonWriter);

        generator.writeStartArray();

        for (AddressLookup address : (AddressLookup[]) batch.getLookups().values().toArray()){
            generator.writeStartObject();

            if (address.getInputId() != null)
                generator.write("input_id", address.getInputId());
            if (address.getStreet() != null)
                generator.write("street", address.getStreet());
            if (address.getStreet2() != null)
                generator.write("street2", address.getStreet2());
            if (address.getSecondary() != null)
                generator.write("secondary", address.getSecondary());
            if (address.getCity() != null)
                generator.write("city", address.getCity());
            if (address.getState() != null)
                generator.write("state", address.getState());
            if (address.getZipcode() != null)
                generator.write("zipcode", address.getZipcode());
            if (address.getLastline() != null)
                generator.write("lastline", address.getLastline());
            if (address.getAddressee() != null)
                generator.write("addressee", address.getAddressee());
            if (address.getUrbanization() != null)
                generator.write("urbanization", address.getUrbanization());
            if (address.getMaxCandidates() != 1)
                generator.write("candidates", address.getMaxCandidates());

            generator.writeEnd();
        }

        generator.writeEnd();
        payload = jsonWriter.toString();
        generator.close();

        return payload;
    }

    // Loads the raw JSON response into Candidate objects, and puts those into the appropriate AddressLookups
    private void deserializeResponse(String rawJSON, Batch batch) {
        JsonArray array = Json.createReader(new StringReader(rawJSON)).readArray();

        for (JsonValue val : array) {
            JsonObject obj = (JsonObject) val;

            Candidate candidate = new Candidate(obj);

            int index = candidate.getInputIndex();
            batch.get(index).addToResult(candidate);
        }
    }

    private void copyHeaders(Batch batch, Request request) {
        if (batch.getIncludeInvalid())
            request.addHeader("X-Include-Invalid", "true");
        else if (batch.getStandardizeOnly())
            request.addHeader("X-Standardize-Only", "true");

    }
}
