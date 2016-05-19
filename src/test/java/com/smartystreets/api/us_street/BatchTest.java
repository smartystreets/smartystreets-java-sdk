package com.smartystreets.api.us_street;

import com.smartystreets.api.exceptions.BatchFullException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BatchTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void testGetsLookupByInputId() throws Exception {
        Batch batch = new Batch();
        AddressLookup lookup = new AddressLookup().setInputId("hasInputId");

        batch.add(lookup);

        assertEquals(lookup, batch.get("hasInputId"));
    }

    @Test
    public void testGetsLookupByIndex() throws Exception {
        Batch batch = new Batch();
        AddressLookup lookup = new AddressLookup();

        batch.add(lookup);

        assertEquals(lookup, batch.get(0));
    }

    @Test
    public void testReturnsCorrectSize() throws Exception {
        Batch batch = new Batch();

        batch.add(new AddressLookup());
        batch.add(new AddressLookup());
        batch.add(new AddressLookup());

        assertEquals(3, batch.size());
    }

    @Test
    public void testAddingALookupWhenBatchIsFullThrowsException() throws Exception {
        Batch batch = new Batch();
        AddressLookup lookup = new AddressLookup();

        exception.expect(BatchFullException.class);

        for (int i = 0; i < Batch.MAX_BATCH_SIZE + 1; i++)
            batch.add(lookup);
    }

    @Test
    public void testResetMethodResetsHeadersAndLookups() throws Exception {
        Batch batch = new Batch();
        batch.setIncludeInvalid(true);
        batch.setStandardizeOnly(true);
        batch.add(new AddressLookup());

        batch.reset();

        assertEquals(0, batch.getAllLookups().size());
        assertEquals(0, batch.getNamedLookups().size());
        assertFalse(batch.getIncludeInvalid());
        assertFalse(batch.getStandardizeOnly());
    }

    @Test
    public void testClearMethodClearsBothLookupCollectionsButNotHeaders() throws Exception {
        Batch batch = new Batch();
        batch.setIncludeInvalid(true);
        batch.setStandardizeOnly(true);
        batch.add(new AddressLookup());

        batch.clear();

        assertEquals(0, batch.getAllLookups().size());
        assertEquals(0, batch.getNamedLookups().size());
        assertTrue(batch.getIncludeInvalid());
        assertTrue(batch.getStandardizeOnly());
    }
}