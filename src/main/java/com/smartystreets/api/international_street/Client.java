package com.smartystreets.api.international_street;

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

    public Candidate[] send(Lookup lookup) throws IOException, SmartyException {
        this.ensureEnoughInfo(lookup);
        Request request = this.buildRequest(lookup);

        Response response = this.sender.send(request);

        Candidate[] candidates = this.serializer.deserialize(response.getPayload(), Candidate[].class);
        lookup.setResult(candidates);
        return candidates;
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();

        request.putParameter("country", lookup.getCountry());
        request.putParameter("geocode", lookup.getGeocode());
        if (lookup.getLanguage() != null)
            request.putParameter("language", lookup.getLanguage().getName());
        request.putParameter("freeform", lookup.getFreeform());
        request.putParameter("address1", lookup.getAddress1());
        request.putParameter("address2", lookup.getAddress2());
        request.putParameter("address3", lookup.getAddress3());
        request.putParameter("address4", lookup.getAddress4());
        request.putParameter("organization", lookup.getOrganization());
        request.putParameter("locality", lookup.getLocality());
        request.putParameter("administrative_area", lookup.getAdministrativeArea());
        request.putParameter("postal_code", lookup.getPostalCode());

        return request;
    }

    private void ensureEnoughInfo(Lookup lookup) throws SmartyException {
        if ((fieldIsSet(lookup.getCountry()) && fieldIsSet(lookup.getFreeform())) ||
                (fieldIsSet(lookup.getCountry()) && fieldIsSet(lookup.getAddress1()) && fieldIsSet(lookup.getPostalCode()))||
                (fieldIsSet(lookup.getCountry()) && fieldIsSet(lookup.getAddress1()) && fieldIsSet(lookup.getLocality())
                        && fieldIsSet(lookup.getAdministrativeArea())))
            return;
        else throw new SmartyException("Insufficient information: One or more required fields were not set on the lookup.");
    }

    private boolean fieldIsSet(String field) {
        return !(field == null || field.isEmpty());
    }
}
