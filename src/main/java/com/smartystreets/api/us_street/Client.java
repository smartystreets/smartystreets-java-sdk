package com.smartystreets.api.us_street;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class Client {
    private final String urlPrefix;
    private final Sender sender;
    private final Serializer serializer;

    public Client(String urlPrefix, Sender sender, Serializer serializer) {
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
            this.populateQueryString(batch, request);
        else
            request.setPayload(this.serializer.serialize(batch.getAllLookups()));

        Response response = this.sender.send(request);
        Candidate[] candidates = this.serializer.deserialize(response.getPayload(), Candidate[].class);
        this.assignCandidatesToLookups(batch, candidates);
    }

    private void appendHeaders(Batch batch, Request request) {
        if (batch.getIncludeInvalid())
            request.addHeader("X-Include-Invalid", "true");
        else if (batch.getStandardizeOnly())
            request.addHeader("X-Standardize-Only", "true");
    }

    private void populateQueryString(Batch batch, Request request) {
        AddressLookup address = batch.get(0);
        request.appendParameter("street", address.getStreet());
        request.appendParameter("street2", address.getStreet2());
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
            for (Candidate candidate : candidates) {
                if (candidate.getInputIndex() == i) {
                    batch.get(i).addToResult(candidate);
                }
            }
        }
    }
}
