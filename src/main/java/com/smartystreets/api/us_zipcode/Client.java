package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.Credentials;
import com.smartystreets.api.Request;
import com.smartystreets.api.Sender;

public class Client {
    private Credentials signer;
    private Sender inner;

    public Client(Credentials signer, Sender inner) {
        this.signer = signer;
        this.inner = inner;
    }

    static void serializeGET(Batch batch, Request request) {
        Lookup lookup = batch.get(0);

        request.appendParameter("input_id", lookup.getInputId());
        request.appendParameter("city", lookup.getCity());
        request.appendParameter("state", lookup.getState());
        request.appendParameter("zipcode", lookup.getZipcode());
    }

    static void serializePOST(Batch batch, Request request) {

    }


}
