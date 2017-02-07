package com.smartystreets.api.us_autocomplete;


import com.smartystreets.api.*;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.ArrayList;

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
        request.putParameter("city_filter", buildFilterString(lookup.getCityFilter()));
        request.putParameter("state_filter", buildFilterString(lookup.getStateFilter()));
        request.putParameter("prefer", buildFilterString(lookup.getPrefer()));
        if (lookup.getGeolocateType() != GeolocateType.NONE) {
            request.putParameter("geolocate", "true");
            request.putParameter("geolocate_precision", lookup.getGeolocateType().getName());
        }

        return request;
    }

    private String buildFilterString(ArrayList<String> list) {
        if (list.isEmpty())
            return null;

        String filterList = "";

        for (String item : list) {
            filterList += (item + ",");
        }

        if (filterList.endsWith(","))
            filterList = filterList.substring(0, filterList.length()-1);

        return filterList;
    }
}
