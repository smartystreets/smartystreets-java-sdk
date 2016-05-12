package com.smartystreets.api.us_street;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.smartystreets.api.Request;

import java.io.IOException;
import java.io.StringWriter;

class GoogleInterpreter {

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
        //TODO: Make this take in either a String or a byte stream instead of an HttpResponse
        Candidate[] candidates = response.parseAs(Candidate[].class);

        for (int i = 0; i < batch.size(); i++) {
            for (int j = 0; j < candidates.length; j++) {
                if (candidates[j].getInputIndex() == i) {
                    batch.get(i).addToResult(candidates[j]);
                }
            }
        }
    }
}
