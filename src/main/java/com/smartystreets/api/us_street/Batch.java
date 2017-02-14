package com.smartystreets.api.us_street;

import com.smartystreets.api.exceptions.BatchFullException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * This class contains a collection of lookups to be sent to the <br>
 *     SmartyStreets US Street API all at once. This is more efficient than sending them<br>
 *     one at a time.
 */
public class Batch {
    public static final int MAX_BATCH_SIZE = 100;
    private Map<String, Lookup> namedLookups;
    private Vector<Lookup> allLookups;

    public Batch() {
        this.namedLookups = new LinkedHashMap<>();
        this.allLookups = new Vector<>();
    }

    public void add(Lookup newAddress) throws BatchFullException {
        if (this.isFull())
            throw new BatchFullException("Batch size cannot exceed " + MAX_BATCH_SIZE);

        this.allLookups.add(newAddress);

        String key = newAddress.getInputId();
        if (key == null)
            return;

        this.namedLookups.put(key, newAddress);

    }

    /**
     * Clears the lookups stored in the batch so it can be used again.<br>
     *     This helps avoid the overhead of building a new Batch object for each group of lookups.
     */
    public void clear() {
        this.namedLookups.clear();
        this.allLookups.clear();
    }

    //region [ Helpers ]

    /**
     * @return The number of lookups currently in this batch.
     */
    public int size() {
        return this.allLookups.size();
    }

    public boolean isFull() {
        return (this.size() >= MAX_BATCH_SIZE);
    }

    public Iterator<Lookup> iterator() {
        return this.allLookups.iterator();
    }

    //endregion

    //region [ Getters ]

    public Map<String, Lookup> getNamedLookups() {
        return this.namedLookups;
    }

    public Lookup get(String inputId) {
        return this.namedLookups.get(inputId);
    }

    public Lookup get(int inputIndex) {
        return this.allLookups.get(inputIndex);
    }

    public Vector<Lookup> getAllLookups() {
        return this.allLookups;
    }

    //endregion
}
