package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.exceptions.BatchFullException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BatchTest {
    @Test
    public void testGetsLookupsByInputId() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup().setInputId("hasInputId");

        batch.add(lookup);

        assertNotNull(batch.get("hasInputId"));
    }

    @Test
    public void testGetsLookupsByIndex() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();
        lookup.setCity("Provo");

        batch.add(lookup);

        assertNull(batch.get(1));
        assertEquals("Provo", batch.get(0).getCity());
    }

    @Test
    public void testReturnsCorrectSize() throws Exception {
        Batch batch = new Batch();

        Lookup lookup = new Lookup().setInputId("inputId");
        Lookup lookup1 = new Lookup();
        Lookup lookup2 = new Lookup();

        batch.add(lookup);
        batch.add(lookup1);
        batch.add(lookup2);

        assertEquals(3, batch.size());
    }

    @Test
    public void testAddingALookupWhenBatchIsFullThrowsException() {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();

        String exMessage = "";
        try {
            for (int i = 0; i < 100; i++) {
                batch.add(lookup);
            }
        } catch (BatchFullException ex) {
            exMessage = ex.getMessage();
        } finally {
            assertEquals(batch.MAX_BATCH_SIZE, batch.size());
            assertEquals("Batch size cannot exceed " + batch.MAX_BATCH_SIZE, exMessage);
        }
    }

    @Test
    public void testClearMethodClearsBothLookupCollections() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();
        batch.add(lookup);
        batch.add(lookup);

        batch.clear();

        assertEquals(0, batch.getAllLookups().size());
        assertEquals(0, batch.getNamedLookups().size());
    }

}