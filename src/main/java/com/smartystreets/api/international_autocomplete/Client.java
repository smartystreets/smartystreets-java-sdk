package com.smartystreets.api.international_autocomplete;

import com.smartystreets.api.*;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

/**
 * This client sends lookups to the SmartyStreets US Autocomplete API, <br>
 *     and attaches the results to the appropriate Lookup objects.
 */
public class Client {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public Candidate[] send(Lookup lookup) throws SmartyException, IOException {
        if (lookup == null || lookup.getSearch() == null || lookup.getSearch().length() == 0)
            throw new SmartyException("Send() must be passed a Lookup with the prefix field set.");

        Request request = this.buildRequest(lookup);

        Response response = this.sender.send(request);

        Result result = this.serializer.deserialize(response.getPayload(), Result.class);
        Candidate[] candidates = result.getCandidates();
        lookup.setResult(candidates);

        return candidates;
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();

        request.putParameter("country", lookup.getCountry());
        request.putParameter("search", lookup.getSearch());
        request.putParameter("administrative_area", lookup.getAdministrativeArea());
        request.putParameter("locality", lookup.getLocality());
        request.putParameter("postal_code", lookup.getPostalCode());

        return request;
    }
}
