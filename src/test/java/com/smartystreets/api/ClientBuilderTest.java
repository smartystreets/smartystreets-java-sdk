package com.smartystreets.api;

import com.smartystreets.api.mocks.RequestCapturingSender;
import com.smartystreets.api.us_street.Client;
import com.smartystreets.api.us_street.Lookup;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientBuilderTest {

    @Test
    public void testWithFeatureIANATimeZone() {
        ClientBuilder builder = new ClientBuilder("id", "token")
                .withFeatureIanaTimeZone();

        assertEquals("iana-timezone", builder.getCustomQueries().get("features"));
    }

    @Test
    public void testWithFeatureIANATimeZoneAppendsWhenCombinedWithComponentAnalysis() {
        ClientBuilder builder = new ClientBuilder("id", "token")
                .withFeatureComponentAnalysis()
                .withFeatureIanaTimeZone();

        assertEquals("component-analysis,iana-timezone", builder.getCustomQueries().get("features"));
    }

    @Test(expected = IllegalStateException.class)
    public void testWithSender_ThrowsWhenCombinedWithMaxTimeout() throws Exception {
        new ClientBuilder("test-id", "test-token")
                .withSender(new RequestCapturingSender())
                .withMaxTimeout(5000)
                .buildUsStreetApiClient();
    }

    @Test(expected = IllegalStateException.class)
    public void testWithSender_ThrowsWhenCombinedWithProxy() throws Exception {
        new ClientBuilder("test-id", "test-token")
                .withSender(new RequestCapturingSender())
                .withProxy(java.net.Proxy.Type.HTTP, "localhost", 8080)
                .buildUsStreetApiClient();
    }

    @Test
    public void testWithSender_WrapsWithMiddlewareChain() throws Exception {
        RequestCapturingSender capturingSender = new RequestCapturingSender();
        Client client = (Client) new ClientBuilder("test-id", "test-token")
                .withSender(capturingSender)
                .buildUsStreetApiClient();

        Lookup lookup = new Lookup();
        lookup.setStreet("1 Rosedale");
        client.send(lookup);

        String url = capturingSender.getRequest().getUrl();
        assertTrue(url.contains("us-street.api.smarty.com"));
        assertTrue(url.contains("auth-id=test-id"));
        assertTrue(url.contains("auth-token=test-token"));
    }
}
