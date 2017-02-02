package com.smartystreets.api.us_autocomplete;

import com.smartystreets.api.exceptions.BatchFullException;

import java.util.Iterator;
import java.util.Vector;

public class Batch {
    public static final int MAX_BATCH_SIZE = 100;
    private Vector<Lookup> allLookups;

    public Batch() {
        this.allLookups = new Vector<>();
    }

    public void add(Lookup newAddress) throws BatchFullException {
        if (this.isFull())
            throw new BatchFullException("Batch size cannot exceed " + MAX_BATCH_SIZE);

        this.allLookups.add(newAddress);
    }

    public void clear() {
        this.allLookups.clear();
    }

    //region [ Helpers ]

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

    public Lookup get(int inputIndex) {
        return this.allLookups.get(inputIndex);
    }

    public Vector<Lookup> getAllLookups() {
        return this.allLookups;
    }

    //endregion
}
