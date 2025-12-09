package com.smartystreets.api.us_autocomplete_pro;


import com.smartystreets.api.*;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This client sends lookups to the SmartyStreets US Autocomplete Pro API, <br>
 *     and attaches the results to the appropriate Lookup objects.
 */
public class Client {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public Suggestion[] send(Lookup lookup) throws SmartyException, IOException, InterruptedException {
        if (lookup == null || lookup.getSearch() == null || lookup.getSearch().length() == 0)
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

        request.putParameter("search", lookup.getSearch());
        request.putParameter("max_results", lookup.getMaxSuggestionsStringIfSet());
        request.putParameter("include_only_cities", this.buildString(lookup.getCityFilter()));
        request.putParameter("include_only_states", this.buildString(lookup.getStateFilter()));
        request.putParameter("include_only_zip_codes", this.buildString(lookup.getZipcodeFilter()));
        request.putParameter("exclude_states", this.buildString(lookup.getExcludeStates()));
        request.putParameter("prefer_cities", this.buildString(lookup.getPreferCity()));
        request.putParameter("prefer_states", this.buildString(lookup.getPreferState()));
        request.putParameter("prefer_zip_codes", this.buildString(lookup.getPreferZipcode()));
        request.putParameter("prefer_ratio", lookup.getPreferRatioStringIfSet());
        if (lookup.getGeolocateType() != null) {
            request.putParameter("prefer_geolocation", lookup.getGeolocateType().getName());
        }
        request.putParameter("selected", lookup.getSelected());
        request.putParameter("source", lookup.getSource());

        for (Map.Entry<String, String> entry : lookup.getCustomParamMap().entrySet()) {
            request.putParameter(entry.getKey(), entry.getValue());
        }

        return request;
    }

    private String buildString(List<String> list) {
        return buildStringFromList(list, ";");
    }

    private String buildStringFromList(List<String> list, String separator) {
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
