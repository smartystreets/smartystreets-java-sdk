package com.smartystreets.api;


import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.mocks.MockSender;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LicenseSenderTest {

    @Test
    public void testSetsLicenses() throws IOException, SmartyException {
        Request request = new Request();
        ArrayList<String> licenses = new ArrayList<String>();
        licenses.add("one");
        licenses.add("two");
        licenses.add("three");
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new LicenseSender(licenses, inner);

        Response response = sender.send(request);

        assertEquals("?license=one%2Ctwo%2Cthree", request.getUrl());
    }

    @Test
    public void testLicensesNotSet() throws IOException, SmartyException {
        Request request = new Request();
        ArrayList<String> licenses = new ArrayList<String>();
        Sender inner = new MockSender(new Response(123, null));
        Sender sender = new LicenseSender(licenses, inner);

        Response response = sender.send(request);

        assertEquals("?", request.getUrl());
    }
}
