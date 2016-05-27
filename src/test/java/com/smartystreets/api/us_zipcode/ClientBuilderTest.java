package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.GoogleSender;
import com.smartystreets.api.GoogleSerializer;
import com.smartystreets.api.RetrySender;
import com.smartystreets.api.Sender;
import com.smartystreets.api.mocks.MockSender;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClientBuilderTest {

    @Test
    public void testBuild() throws Exception {
        /** Case 1: Default build **/
        Client client = new ClientBuilder().build();

        assertNotNull(client);

        /**Case 2: Custom build*/
        client = new ClientBuilder()
                .withUrl("testUrl")
                .withSender(new MockSender(null))
                .withSerializer(new GoogleSerializer())
                .build();

//        assertEquals("testUrl", client.urlPrefix);
//        assertEquals(MockStatusCodeSender.class, );
//        assertNotNull(client.serializer);
    }

    /*@Test
    public void testBuildSender() throws Exception {
        *//** Case 1: Default build **//*
        RetrySender retrySender = (RetrySender) new ClientBuilder().buildSender();
        GoogleSender googleSender = (GoogleSender) retrySender.getInner();

        assertNotNull(retrySender);
        assertEquals(5, retrySender.getMaxRetries());

        assertNotNull(googleSender);
        assertEquals(10000, googleSender.getMaxTimeOut());

        *//** Case 2: Custom build **//*
        retrySender = (RetrySender) new ClientBuilder()
                .retryAtMost(10)
                .withMaxTimeout(500)
                .buildSender();
        googleSender = (GoogleSender) retrySender.getInner();

        assertNotNull(retrySender);
        assertEquals(10, retrySender.getMaxRetries());

        assertNotNull(googleSender);
        assertEquals(500, googleSender.getMaxTimeOut());

        *//** Case 3: MockStatusCodeSender **//*
        Sender mockSender = new ClientBuilder().withSender(new MockSender(null)).buildSender();

        assertEquals(MockSender.class, mockSender.getClass());
    }*/

}