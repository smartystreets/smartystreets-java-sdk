package com.smartystreets.api.us_street;

import com.smartystreets.api.*;
import com.smartystreets.api.exceptions.SmartyException;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

public class Client {
    private Credentials signer;
    private Sender inner;

    public Client (Credentials signer, Sender inner) {
        this.signer = signer;
        this.inner = inner;
    }

    // Wraps address in a batch and calls the other send method
    public void send(AddressLookup lookup) throws SmartyException, IOException {
        Batch batch = new Batch();
        batch.add(lookup);
        this.send(batch);
    }

    // Sends lookup to the US street API
    public void send(Batch batch) throws IOException, SmartyException {
        // New Request
        Request request = new Request("https://api.smartystreets.com/street-address?");

        // Determine if it is a single address or not, set method and serialize
        if (batch.size() == 0)
            return;
        if (batch.size() == 1) {
            request.setMethod("GET");
            // Add credentials to url

            // this.serializeGET(request)
            this.signer.sign(request);
            this.serializeGET(batch, request);
        } else {
            request.setMethod("POST");
            // Add credentials to url
            this.signer.sign(request);
            request.addHeader("Content-Type", "application/json");
            request.setJsonPayload(this.serializePOST(batch));
        }

        copyHeaders(batch, request);

        // Send request to API, and interpret the response
        Response response = this.inner.send(request); // can throw exceptions
        this.deserializeResponse(response.getRawJSON(), batch);
    }

    static void serializeGET(Batch batch, Request request) throws UnsupportedEncodingException {
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

    static String serializePOST(Batch batch) {
        String payload;
        StringWriter jsonWriter = new StringWriter();
        JsonGenerator generator = Json.createGenerator(jsonWriter);

        generator.writeStartArray();

        for (AddressLookup address : batch.getAllLookups()){
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
        generator.close();
        payload = jsonWriter.toString();

//        System.out.println("payload: " + payload);

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
