package com.smartystreets.api.us_autocomplete;


import com.smartystreets.api.*;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.ArrayList;

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

    public Suggestion[] send(Lookup lookup) throws SmartyException, IOException {
        if (lookup == null || lookup.getPrefix() == null || lookup.getPrefix().length() == 0)
            throw new SmartyException("Send() must be passed a Lookup with the prefix field set.");

        Request request = this.buildRequest(lookup);

        Response response = this.sender.send(request);

        Result result = this.serializer.deserialize(response.getPayload(), Result.class);
        Suggestion[] suggestions = result.getSuggestions();
        lookup.setResult(suggestions);

        return suggestions;
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();

        request.putParameter("prefix", lookup.getPrefix());
        request.putParameter("suggestions", lookup.getMaxSuggestionsStringIfSet());
        request.putParameter("city_filter", this.buildFilterString(lookup.getCityFilter()));
        request.putParameter("state_filter", this.buildFilterString(lookup.getStateFilter()));
        request.putParameter("prefer", this.buildPreferString(lookup.getPrefer()));
        request.putParameter("prefer_ratio", lookup.getPreferRatioStringIfSet());
        if (lookup.getGeolocateType() != GeolocateType.NONE) {
            request.putParameter("geolocate", "true");
            request.putParameter("geolocate_precision", lookup.getGeolocateType().getName());
        }
        else request.putParameter("geolocate", "false");

        return request;
    }

    private String buildPreferString(ArrayList<String> list) {
        return buildStringFromList(list, ";");
    }

    private String buildFilterString(ArrayList<String> list) {
        return buildStringFromList(list, ",");
    }

    private String buildStringFromList(ArrayList<String> list, String separator) {
        if (list.isEmpty())
            return null;

        String filterList = "";

        for (String item : list) {
            filterList += (item + separator);
        }

        if (filterList.endsWith(separator))
            filterList = filterList.substring(0, filterList.length()-1);

        return filterList;
    }
}
