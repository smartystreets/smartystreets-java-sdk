package com.smartystreets.api.us_street;

import com.smartystreets.api.exceptions.BatchFullException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BatchTest {

    @Test
    public void testGetsLookupByInputId() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup().setInputId("hasInputId");

        batch.add(lookup);

        assertEquals(lookup, batch.get("hasInputId"));
    }

    @Test
    public void testGetsLookupByIndex() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();

        batch.add(lookup);

        assertEquals(lookup, batch.getAllLookups().get(0));
    }

    @Test
    public void testReturnsCorrectSize() throws Exception {
        Batch batch = new Batch();

        batch.add(new Lookup());
        batch.add(new Lookup());
        batch.add(new Lookup());

        assertEquals(3, batch.size());
    }

    @Test
    public void testAddingALookupWhenBatchIsFullThrowsException() {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();

        assertThrows(BatchFullException.class, () -> {
            for (int i = 0; i < Batch.MAX_BATCH_SIZE + 1; i++) {
                batch.add(lookup);
            }
        });
    }

}