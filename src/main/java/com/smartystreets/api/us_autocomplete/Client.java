package com.smartystreets.api.us_autocomplete;


import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;

public class Client {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public void send(Lookup lookup) throws SmartyException, IOException {
        if (lookup == null || lookup.getPrefix() == null || lookup.getPrefix().length() == 0)
            throw new SmartyException("Send() must be passed a Lookup with the prefix field set.");

        Request request = this.buildRequest(lookup);
        Response response = this.sender.send(request);
        lookup.setResult(this.serializer.deserialize(response.getPayload(), Result.class));
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();

        request.putParameter("prefix", lookup.getPrefix());
        request.putParameter("suggestions", Integer.toString(lookup.getMaxSuggestions()));
        request.putParameter("city_filter", lookup.getCityFilter());
        request.putParameter("state_filter", lookup.getStateFilter());
        request.putParameter("prefer", lookup.getPrefer());
        request.putParameter("geolocate", Boolean.toString(lookup.getGeolocate()));
        request.putParameter("geolocate_precision", lookup.getGeolocatePrecision());

        return request;
    }
}
