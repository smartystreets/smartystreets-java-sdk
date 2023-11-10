package com.smartystreets.api.us_enrichment;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_enrichment.Lookup;
import com.smartystreets.api.us_enrichment.Result;

import java.io.IOException;

public class Client {
    private Sender sender;
    private Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    private Result send(Lookup lookup) throws IOException, SmartyException, InterruptedException {
        if (lookup == null || lookup.getSmartyKey() == null || lookup.getSmartyKey().isEmpty())
            throw new SmartyException("Client.send() requires a Lookup with the 'smartyKey' field set");

        Request request = this.buildRequest(lookup);
        Response response = this.sender.send(request);
        Result result = this.serializer.deserialize(response.getPayload(), Result.class);

        lookup.setResults(result);
        return result;
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();
        request.setUrlPrefix(lookup.getSmartyKey() + "/" + lookup.getDatasetName() + "/" + lookup.getDataSubsetName());
        return request;
    }
}
