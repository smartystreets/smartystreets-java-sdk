package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

/**
 * This client sends lookups to the SmartyStreets US ZIP Code API, <br>
 *     and attaches the results to the appropriate Lookup objects.
 */
public class Client {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public void send(Lookup lookup) throws SmartyException, IOException {
        Batch batch = new Batch();
        batch.add(lookup);
        send(batch);
    }

    public void send(Batch batch) throws SmartyException, IOException {
        Request request = new Request();

        if (batch.size() == 0)
            throw new SmartyException("Batch must contain between 1 and 100 lookups");

        if (batch.size() == 1)
            this.populateQueryString(batch.get(0), request);
        else
            request.setPayload(this.serializer.serialize(batch.getAllLookups()));

        Response response = this.sender.send(request);

        Result[] results = this.serializer.deserialize(response.getPayload(), Result[].class);
        if (results == null)
            results = new Result[0];
        this.assignResultsToLookups(batch, results);
    }

    private void populateQueryString(Lookup lookup, Request request) {
        request.putParameter("input_id", lookup.getInputId());
        request.putParameter("city", lookup.getCity());
        request.putParameter("state", lookup.getState());
        request.putParameter("zipcode", lookup.getZipCode());
    }


    private void assignResultsToLookups(Batch batch, Result[] results) {
        for (int i = 0; i < results.length; i++) {
            batch.get(i).setResult(results[i]);
        }
    }
}
