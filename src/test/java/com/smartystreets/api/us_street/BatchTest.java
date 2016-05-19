package com.smartystreets.api.us_street;

import com.smartystreets.api.exceptions.BatchFullException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BatchTest {

    @Test
    public void testGetsLookupsByInputId() throws Exception {
        Batch batch = new Batch();
        AddressLookup lookup = new AddressLookup().setInputId("hasInputId");

        batch.add(lookup);

        assertNotNull(batch.get("hasInputId"));
    }

    @Test
    public void testGetsLookupsByIndex() throws Exception {
        Batch batch = new Batch();
        AddressLookup lookup = new AddressLookup();
        lookup.setCity("Provo");

        batch.add(lookup);

//        assertNull(batch.get(1));
        assertEquals("Provo", batch.get(0).getCity());
    }

    @Test
    public void testReturnsCorrectSize() throws Exception {
        Batch batch = new Batch();

        AddressLookup lookup = new AddressLookup().setInputId("inputId");
        AddressLookup lookup1 = new AddressLookup();
        AddressLookup lookup2 = new AddressLookup();

        batch.add(lookup);
        batch.add(lookup1);
        batch.add(lookup2);

        assertEquals(3, batch.size());
    }

    @Test
    public void testAddingALookupWhenBatchIsFullThrowsException() {
        Batch batch = new Batch();
        AddressLookup lookup = new AddressLookup();

        String exMessage = "";
        try {
            for (int i = 0; i < 101; i++) {
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
    public void testResetMethodResetsHeadersAndLookups() throws Exception {
        Batch batch = new Batch();
        batch.setIncludeInvalid(true);
        batch.setStandardizeOnly(true);
        AddressLookup lookup = new AddressLookup();
        batch.add(lookup);
        batch.add(lookup);

        batch.reset();

        assertEquals(0, batch.getAllLookups().size());
        assertEquals(0, batch.getNamedLookups().size());
        assertFalse(batch.getIncludeInvalid());
        assertFalse(batch.getStandardizeOnly());
    }

    @Test
    public void testClearMethodClearsBothLookupCollections() throws Exception {
        Batch batch = new Batch();
        AddressLookup lookup = new AddressLookup();
        batch.add(lookup);
        batch.add(lookup);

        batch.clear();

        assertEquals(0, batch.getAllLookups().size());
        assertEquals(0, batch.getNamedLookups().size());
    }

}