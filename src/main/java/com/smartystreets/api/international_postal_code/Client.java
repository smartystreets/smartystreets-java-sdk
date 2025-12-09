package com.smartystreets.api.international_postal_code;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.exceptions.UnprocessableEntityException;
import java.io.IOException;
import java.util.Map;

/**
 * Client for the International Postal Code API. Sends Lookup objects and populates results.
 * @see "https://smartystreets.com/docs/cloud/international-postal-code-api"
 */
public class Client {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public Candidate[] send(Lookup lookup) throws IOException, SmartyException, InterruptedException {
        this.ensureEnoughInfo(lookup);
        Request request = this.buildRequest(lookup);
        Response response = this.sender.send(request);
        Candidate[] candidates = this.serializer.deserialize(response.getPayload(), Candidate[].class);
        lookup.setResult(candidates);
        return candidates;
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();
        request.putParameter("input_id", lookup.getInputId());
        request.putParameter("country", lookup.getCountry());
        request.putParameter("locality", lookup.getLocality());
        //request.putParameter("language", lookup.getLanguage()); // future
        //request.putParameter("features", lookup.getFeatures()); // future
        request.putParameter("administrative_area", lookup.getAdministrativeArea());
        request.putParameter("postal_code", lookup.getPostalCode());

        for (Map.Entry<String, String> entry : lookup.getCustomParamMap().entrySet()) {
            request.putParameter(entry.getKey(), entry.getValue());
        }

        return request;
    }

    private void ensureEnoughInfo(Lookup lookup) throws SmartyException {
        if (lookup == null)
            throw new UnprocessableEntityException("Lookup cannot be null.");
        if (isMissing(lookup.getCountry()))
            throw new UnprocessableEntityException("Country field is required.");
        if (!isMissing(lookup.getLocality()) || !isMissing(lookup.getAdministrativeArea()) || !isMissing(lookup.getPostalCode()))
            return;
        throw new UnprocessableEntityException("At least one of locality, administrative_area, or postal_code must be set.");
    }

    private boolean isMissing(String value) {
        return value == null || value.isEmpty();
    }
}
