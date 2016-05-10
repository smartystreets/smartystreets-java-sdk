package com.smartystreets.api.us_zipcode;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.smartystreets.api.Credentials;
import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;

public class Client {
    private Credentials signer;
    private Sender inner;
    HttpTransport transport;

    public Client(Credentials signer, Sender inner) {
        this.signer = signer;
        this.inner = inner;
        this.transport = new NetHttpTransport();
    }

    public void send(Lookup lookup) throws SmartyException, IOException {
        Batch batch = new Batch();
        batch.add(lookup);
        send(batch);
    }

    public void send(Batch batch) throws SmartyException, IOException {
        HttpRequestFactory factory = this.transport.createRequestFactory();
        String baseUrl = "https://us-zipcode.api.smartystreets.com/lookup?";
        Request request = new Request(baseUrl);

        if (batch.size() == 0) return;

        if (batch.size() == 1) {
            request.setMethod("GET");

            // Add credentials to url
            this.signer.sign(request);
            this.serializeIntoRequestUrl(batch, request);

            request.setRequest(factory.buildGetRequest(new GenericUrl(request.getUrlString())));
        }
        else {
            request.setMethod("POST");

            this.signer.sign(request);

            this.serializeIntoRequestBody(batch, request);

            request.setRequest(factory.buildPostRequest(new GenericUrl(baseUrl),
                    new JsonHttpContent(new JacksonFactory(),
                    batch.getAllLookups()).setMediaType(new HttpMediaType(Json.MEDIA_TYPE))));

         //   System.out.println(request.getRequest().getHeaders().getContentType());
        }

        Response response = this.inner.send(request);
        this.deserializeResponse(batch, response.getInnerResponse());
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

    void setTransport(HttpTransport transport) {
        this.transport = transport;
    }
}
