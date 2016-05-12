package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.HttpSender;
import com.smartystreets.api.RetrySender;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientBuilderTest {

    @Test
    public void testBuild() throws Exception {
        /** Case 1: Default build **/
        Client client = new ClientBuilder().build();

        assertNotNull(client);
        assertNotNull(client.inner);
    }

    @Test
    public void testBuildSender() throws Exception {
        /** Case 1: Default build **/
        RetrySender retrySender = (RetrySender) new ClientBuilder().buildSender();
        HttpSender httpSender = (HttpSender) retrySender.getInner();

        assertNotNull(retrySender);
        assertEquals(5, retrySender.getMaxRetries());

        assertNotNull(httpSender);
        assertEquals(10000, httpSender.getMaxTimeOut());

        /** Case 2: Custom build **/
        retrySender = (RetrySender) new ClientBuilder().retryAtMost(10).withMaxTimeout(500).buildSender();
        httpSender = (HttpSender) retrySender.getInner();

        assertNotNull(retrySender);
        assertEquals(10, retrySender.getMaxRetries());

        assertNotNull(httpSender);
        assertEquals(500, httpSender.getMaxTimeOut());
    }

}