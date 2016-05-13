package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.GoogleSender;
import com.smartystreets.api.RetrySender;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientBuilderTest {

    @Test
    public void testBuild() throws Exception {
        /** Case 1: Default build **/
        Client client = new ClientBuilder().build();

        assertNotNull(client);
        assertNotNull(client.sender);
    }

    @Test
    public void testBuildSender() throws Exception {
        /** Case 1: Default build **/
        RetrySender retrySender = (RetrySender) new ClientBuilder().buildSender();
        GoogleSender googleSender = (GoogleSender) retrySender.getInner();

        assertNotNull(retrySender);
        assertEquals(5, retrySender.getMaxRetries());

        assertNotNull(googleSender);
        assertEquals(10000, googleSender.getMaxTimeOut());

        /** Case 2: Custom build **/
        retrySender = (RetrySender) new ClientBuilder().retryAtMost(10).withMaxTimeout(500).buildSender();
        googleSender = (GoogleSender) retrySender.getInner();

        assertNotNull(retrySender);
        assertEquals(10, retrySender.getMaxRetries());

        assertNotNull(googleSender);
        assertEquals(500, googleSender.getMaxTimeOut());
    }

}