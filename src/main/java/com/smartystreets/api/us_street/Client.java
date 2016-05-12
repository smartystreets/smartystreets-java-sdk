package com.smartystreets.api.us_street;

import java.io.IOException;
import com.smartystreets.api.*;
import com.smartystreets.api.exceptions.SmartyException;

public class Client {
    private final String urlPrefix;
    private final Sender sender;
    private final Serializer serializer;

    public Client (String urlPrefix, Sender sender, Serializer serializer) {
        this.urlPrefix = urlPrefix;
        this.sender = sender;
        this.serializer = serializer;
    }

    public void send(AddressLookup lookup) throws SmartyException, IOException {
        Batch batch = new Batch();
        batch.add(lookup);
        this.send(batch);
    }

    public void send(Batch batch) throws SmartyException, IOException {
        Request request = new Request(this.urlPrefix);

        if (batch.size() == 0)
            return;

        this.appendHeaders(batch, request);

        if (batch.size() == 1)
            populateQueryString(batch, request);
        else
            request.setPayload(this.serializer.serialize(batch.getAllLookups()));

        Response response = this.sender.send(request);
        Candidate[] candidates = (Candidate[]) this.serializer.deserialize(response.getPayload(), Candidate[].class);
        assignCandidatesToLookups(batch, candidates);
    }

    private void appendHeaders(Batch batch, Request request) {
        if (batch.getIncludeInvalid())
            request.addHeader("X-Include-Invalid", "true");
        else if (batch.getStandardizeOnly())
            request.addHeader("X-Standardize-Only", "true");
    }

    private static void populateQueryString(Batch batch, Request request) {
        AddressLookup address = batch.get(0);
        request.appendParameter("input_id", address.getInputId());
        request.appendParameter("input_id", address.getInputId());
        request.appendParameter("street", address.getStreet());
        request.appendParameter("street2",address.getStreet2());
        request.appendParameter("secondary", address.getSecondary());
        request.appendParameter("city", address.getCity());
        request.appendParameter("state", address.getState());
        request.appendParameter("zipcode", address.getZipCode());
        request.appendParameter("lastline", address.getLastline());
        request.appendParameter("addressee", address.getAddressee());
        request.appendParameter("urbanization", address.getUrbanization());

        if (address.getMaxCandidates() != 1)
            request.appendParameter("candidates", Integer.toString(address.getMaxCandidates()));
    }

    private void assignCandidatesToLookups(Batch batch, Candidate[] candidates) {
        for (int i = 0; i < batch.size(); i++) {
            for (int j = 0; j < candidates.length; j++) {
                if (candidates[j].getInputIndex() == i) {
                    batch.get(i).addToResult(candidates[j]);
                }
            }
        }
    }
}




//private HttpTransport transport;
//this.transport = new NetHttpTransport();

// request.setInnerRequest(factory.buildGetRequest(new GenericUrl(request.getUrlString())));
               /* HttpRequest innerRequest = factory.buildPostRequest(new GenericUrl(baseUrl),
                new JsonHttpContent(new JacksonFactory(), batch.getAllLookups()));
        innerRequest.getHeaders().setContentType(Json.MEDIA_TYPE);
        request.setInnerRequest(innerRequest);*/


