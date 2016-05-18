package com.smartystreets.api.us_zipcode;

import com.smartystreets.api.exceptions.BatchFullException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class Batch {
    public final int MAX_BATCH_SIZE = 100;
    private Map<String, Lookup> namedLookups;
    private Vector<Lookup> allLookups;

    public Batch() {
        this.namedLookups = new LinkedHashMap<>();
        this.allLookups = new Vector<>();
    }

    public void add(Lookup lookup) throws BatchFullException {
        if (this.allLookups.size() >= MAX_BATCH_SIZE)
            throw new BatchFullException("Batch size cannot exceed " + MAX_BATCH_SIZE);

        String key = lookup.getInputId();

        if (key != null)
            this.namedLookups.put(key, lookup);

        this.allLookups.add(lookup);
    }

    public void clear() {
        this.namedLookups.clear();
        this.allLookups.clear();
    }

    public int size() {
        return this.allLookups.size();
    }

    public Iterator<Lookup> iterator() {
        return this.allLookups.iterator();
    }

    //region [ Getters ]

    public Map<String, Lookup> getNamedLookups() {
        return this.namedLookups;
    }

    public Vector<Lookup> getAllLookups() {
        return this.allLookups;
    }

    public Lookup get(String inputId) {
        return this.namedLookups.get(inputId);
    }

    public Lookup get(int inputIndex) {
        return this.allLookups.get(inputIndex);
    }

    //endregion
}
