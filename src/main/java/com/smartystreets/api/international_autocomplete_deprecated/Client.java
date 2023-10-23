package com.smartystreets.api.international_autocomplete_deprecated;

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

    public Candidate[] send(Lookup lookup) throws SmartyException, IOException, InterruptedException {
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
        request.putParameter("max_results", String.valueOf(lookup.getMaxResults()));
        request.putParameter("distance", String.valueOf(lookup.getDistance()));
        if (lookup.getGeolocation() != InternationalGeolocateType.NONE.getName()) {
            request.putParameter("geolocation", lookup.getGeolocation());
        }
        request.putParameter("include_only_administrative_area", lookup.getAdministrativeArea());
        request.putParameter("include_only_locality", lookup.getLocality());
        request.putParameter("include_only_postal_code", lookup.getPostalCode());
        if (lookup.getLatitude() != null) {
            request.putParameter("latitude", String.valueOf(lookup.getLatitude()));
        }
        if (lookup.getLongitude() != null) {
            request.putParameter("longitude", String.valueOf(lookup.getLongitude()));
        }

        return request;
    }
}
