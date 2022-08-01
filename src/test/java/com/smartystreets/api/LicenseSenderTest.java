package com.smartystreets.api;


import com.smartystreets.api.mocks.MockSender;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LicenseSenderTest {

    @Test
    public void testSetsLicenses() throws Exception {
        Request request = new Request();
        ArrayList<String> licenses = new ArrayList<>();
        licenses.add("one");
        licenses.add("two");
        licenses.add("three");
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new LicenseSender(licenses, inner);

        Response response = sender.send(request);

        assertEquals("?license=one%2Ctwo%2Cthree%2C", request.getUrl());
    }

    @Test
    public void testLicensesNotSet() throws Exception {
        Request request = new Request();
        ArrayList<String> licenses = new ArrayList<>();
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new LicenseSender(licenses, inner);

        Response response = sender.send(request);

        assertEquals("?", request.getUrl());
    }
}
