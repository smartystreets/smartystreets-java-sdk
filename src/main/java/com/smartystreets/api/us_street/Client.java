package com.smartystreets.api.us_street;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.Optional;

/**
 * This client sends lookups to the SmartyStreets US Street API, <br>
 * and attaches the results to the appropriate Lookup objects.
 */
public class Client {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public void send(Lookup lookup) throws SmartyException, IOException, InterruptedException {
        Batch batch = new Batch();
        batch.add(lookup);
        this.send(batch);
    }

    /**
     * Sends a batch of up to 100 lookups for validation.
     *
     * @param batch Batch must contain between 1 and 100 Lookup objects
     * @throws SmartyException
     * @throws IOException
     */
    public void send(Batch batch) throws SmartyException, IOException, InterruptedException {
        Request request = new Request();

        if (batch.size() == 0)
            throw new SmartyException("Batch must contain between 1 and 100 lookups");

        if (batch.size() == 1)
            this.populateQueryString(batch.getAllLookups().get(0), request);
        else
            request.setPayload(this.serializer.serialize(batch.getAllLookups()));


        Response response = this.sender.send(request);

        Candidate[] candidates = null;

        if (response != null) {
            candidates = this.serializer.deserialize(response.getPayload(), Candidate[].class);
        }
        
        if (candidates == null)
            candidates = new Candidate[0];
        this.assignCandidatesToLookups(batch, candidates);
    }

    private void populateQueryString(Lookup address, Request request) {
        request.putParameter("input_id", address.getInputId());
        request.putParameter("street", address.getStreet());
        request.putParameter("street2", address.getStreet2());
        request.putParameter("secondary", address.getSecondary());
        request.putParameter("city", address.getCity());
        request.putParameter("state", address.getState());
        request.putParameter("zipcode", address.getZipCode());
        request.putParameter("lastline", address.getLastline());
        request.putParameter("addressee", address.getAddressee());
        request.putParameter("urbanization", address.getUrbanization());
        request.putParameter("county_source", address.getCountySource());
        request.putParameter("match", address.getMatch());
        request.putParameter("format", address.getFormat());

        if (address.getMaxCandidates() == 1 && (address.getMatch() != null && address.getMatch().equals("enhanced")))
            request.putParameter("candidates", "5");
        else
            request.putParameter("candidates", Integer.toString(address.getMaxCandidates()));
        
        //This is a temporary flag meant to fix an intermittent data issue
        //Unless explicitly instructed by the Smarty Tech Support team, DO NOT use this parameter
        request.putParameter("compatibility", address.getCompatibility());

    }


    private void assignCandidatesToLookups(Batch batch, Candidate[] candidates) {
        for (Candidate candidate : candidates)
            batch.get(candidate.getInputIndex()).addToResult(candidate);
    }
}
