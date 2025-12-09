package com.smartystreets.api.us_extract;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.util.Map;

/**
 * This client sends lookups to the SmartyStreets US Extract API, <br>
 *     and attaches the results to the Lookup objects.
 */
public class Client {
    private Sender sender;
    private Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public Result send(Lookup lookup) throws IOException, SmartyException, InterruptedException {
        if (lookup == null || lookup.getText() == null || lookup.getText().isEmpty())
            throw new SmartyException("Client.send() requires a Lookup with the 'text' field set");

        Request request = this.buildRequest(lookup);
        Response response = this.sender.send(request);
        Result result = this.serializer.deserialize(response.getPayload(), Result.class);

        lookup.setResult(result);
        return result;
    }

    private Request buildRequest(Lookup lookup) {
        Request request = new Request();
        request.setContentType("text/plain");
        request.setPayload(lookup.getText().getBytes());

        request.putParameter("html", lookup.isHtml());
        request.putParameter("aggressive", String.valueOf(lookup.isAggressive()));
        request.putParameter("addr_line_breaks", String.valueOf(lookup.addressesHaveLineBreaks()));
        request.putParameter("addr_per_line", String.valueOf(lookup.getAddressesPerLine()));
        request.putParameter("match", lookup.getMatch());

        for (Map.Entry<String, String> entry : lookup.getCustomParamMap().entrySet()) {
            request.putParameter(entry.getKey(), entry.getValue());
        }

        return request;
    }
}
