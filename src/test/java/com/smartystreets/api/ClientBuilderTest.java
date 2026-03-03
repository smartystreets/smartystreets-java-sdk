package com.smartystreets.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientBuilderTest {

    @Test
    public void testWithFeatureIANATimeZone() {
        ClientBuilder builder = new ClientBuilder("id", "token")
                .withFeatureIANATimeZone();

        assertEquals("iana-timezone", builder.getCustomQueries().get("features"));
    }

    @Test
    public void testWithFeatureIANATimeZoneAppendsWhenCombinedWithComponentAnalysis() {
        ClientBuilder builder = new ClientBuilder("id", "token")
                .withFeatureComponentAnalysis()
                .withFeatureIANATimeZone();

        assertEquals("component-analysis,iana-timezone", builder.getCustomQueries().get("features"));
    }
}
