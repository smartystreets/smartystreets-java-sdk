package com.smartystreets.api.us_autocomplete;


import com.smartystreets.api.exceptions.BatchFullException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class BatchTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetsLookupByIndex() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();

        batch.add(lookup);

        assertEquals(lookup, batch.get(0));
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
    public void testAddingALookupWhenBatchIsFullThrowsException() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();

        exception.expect(BatchFullException.class);

        for (int i = 0; i < Batch.MAX_BATCH_SIZE + 1; i++)
            batch.add(lookup);
    }
}
