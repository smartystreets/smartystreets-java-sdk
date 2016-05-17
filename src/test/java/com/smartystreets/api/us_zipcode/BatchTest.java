package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.exceptions.BatchFullException;
import org.junit.Test;

import javax.swing.plaf.basic.BasicTableHeaderUI;

import static org.junit.Assert.*;

public class BatchTest {

    @Test
    public void testAddingLookups() throws BatchFullException {
        Batch batch = new Batch();

        Lookup hasInputId = new Lookup().setInputId("hasInputId");
        Lookup noInputId = new Lookup();
        noInputId.setCity("Provo");

        batch.add(hasInputId);
        batch.add(noInputId);

        assertEquals(2, batch.size());
        assertNotNull(batch.get("hasInputId"));
        assertEquals("Provo", batch.get(1).getCity());
    }

    @Test
    public void testAddingALookupWhenBatchIsFull() {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();

        String exMessage = "";
        try {
            for (int i = 0; i < 100; i++) {
                batch.add(lookup);
            }
        }
        catch (BatchFullException ex) {
            exMessage = ex.getMessage();
        }
        finally {
            assertEquals(batch.MAX_BATCH_SIZE, batch.size());
            assertEquals("Batch size cannot exceed " + batch.MAX_BATCH_SIZE, exMessage);
        }
    }

    @Test
    public void clear() throws Exception {
        Batch batch = new Batch();
        Lookup lookup = new Lookup();
        batch.add(lookup);
        batch.add(lookup);

        batch.clear();

        assertEquals(0, batch.getAllLookups().size());
        assertEquals(0, batch.getNamedLookups().size());
    }

}