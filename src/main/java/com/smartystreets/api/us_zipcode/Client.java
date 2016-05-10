package com.smartystreets.api.us_zipcode;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonArray;
import com.smartystreets.api.Credentials;
import com.smartystreets.api.Request;
import com.smartystreets.api.Sender;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class Client {
    private Credentials signer;
    private Sender inner;

    public Client(Credentials signer, Sender inner) {
        this.signer = signer;
        this.inner = inner;
    }

    static void serializeIntoRequestUrl(Batch batch, Request request) {
        Lookup lookup = batch.get(0);

        request.appendParameter("input_id", lookup.getInputId());
        request.appendParameter("city", lookup.getCity());
        request.appendParameter("state", lookup.getState());
        request.appendParameter("zipcode", lookup.getZipcode());
    }

    static void serializeIntoRequestBody(Batch batch, Request request) throws IOException {
        JacksonFactory jacksonFactory = new JacksonFactory();
        StringWriter jsonWriter = new StringWriter();
        JsonGenerator generator = jacksonFactory.createJsonGenerator(jsonWriter);

        generator.serialize(batch.getAllLookups());
        generator.close();

        request.setJsonPayload(jsonWriter.toString());
    }

    static void deserializeResponse(Batch batch, HttpResponse response) throws IOException {
        Result[] results = response.parseAs(Result[].class);

        for (int i = 0; i < results.length; i++) {
            batch.get(i).setResult(results[i]);
        }

    }












}
