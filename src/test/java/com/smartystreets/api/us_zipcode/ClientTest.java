package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ClientTest {
    private Batch batch;
    private Lookup lookup1;
    private Lookup lookup2;
    private Lookup lookup3;
    Request request;

    @Before
    public void setData() throws Exception {
        batch = new Batch();
        lookup1 = new Lookup("Provo", "Utah", "84606");
        lookup2 = new Lookup("Salt Lake City", "Utah");
        lookup3 = new Lookup("54321");
        batch.add(lookup1);
        batch.add(lookup2);
        batch.add(lookup3);
        request = new Request();
        request.setUrlString("https://api.smartystreets.com/street-address?");
    }

    @Test
    public void testSerializeGET() throws Exception {
        Client.serializeGET(batch, request);

        assertEquals("https://api.smartystreets.com/street-address?city=Provo&state=Utah&zipcode=84606", request.getUrlString());
    }

    @Test
    public void testSerializePOST() throws Exception {
        Client.serializePOST(batch, request);
        String expectedJsonPayload = "[{\"city\":\"Provo\",\"state\":\"Utah\",\"zipcode\":84606},{\"city\":\"Salt Lake City\",\"state\":\"Utah\"},{\"zipcode\":\"54321\"}]\n";
        assertEquals(expectedJsonPayload, request.getJsonPayload());
    }
}