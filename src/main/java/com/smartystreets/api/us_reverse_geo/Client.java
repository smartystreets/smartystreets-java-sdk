package com.smartystreets.api.us_reverse_geo;

import com.smartystreets.api.Request;
import com.smartystreets.api.Response;
import com.smartystreets.api.Sender;
import com.smartystreets.api.Serializer;
import com.smartystreets.api.exceptions.SmartyException;

import java.io.IOException;
import java.text.DecimalFormat;

public class Client {
    private final Sender sender;
    private final Serializer serializer;

    public Client(Sender sender, Serializer serializer) {
        this.sender = sender;
        this.serializer = serializer;
    }

    public void send(Lookup lookup) throws SmartyException, IOException, InterruptedException {
        Request request = new Request();

        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        request.putParameter("latitude", decimalFormat.format(lookup.getLatitude()));
        request.putParameter("longitude", decimalFormat.format(lookup.getLongitude()));
        request.putParameter("source", getSource();

        Response httpResponse = this.sender.send(request);

        SmartyResponse response = this.serializer.deserialize(httpResponse.getPayload(), SmartyResponse.class);
        lookup.setResponse(response);
    }
}
