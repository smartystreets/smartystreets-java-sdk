package com.smartystreets.api.us_street;

import com.smartystreets.api.exceptions.BatchFullException;

import java.util.*;

public class Batch {
    public final int MAX_BATCH_SIZE = 100;
    private Map<String, AddressLookup> namedLookups;
    private Vector<AddressLookup> allLookups;
    private boolean standardizeOnly, includeInvalid;

    public Batch(){
        this.standardizeOnly = false;
        this.includeInvalid = false;
        this.namedLookups = new LinkedHashMap<>();
        this.allLookups = new Vector<>();
    }

    public void add(AddressLookup newAddress) throws BatchFullException{
        if (this.allLookups.size() >= MAX_BATCH_SIZE)
            throw new BatchFullException("Batch size cannot exceed " + MAX_BATCH_SIZE);

        String key = newAddress.getInputId();

        if (key != null)
            this.namedLookups.put(key, newAddress);

        this.allLookups.add(newAddress);
    }

    public void clear(){
        this.namedLookups.clear();
        this.allLookups.clear();
    }

    public void reset() {
        this.clear();
        this.standardizeOnly = false;
        this.includeInvalid = false;
    }

    public int size() {
        return this.allLookups.size();
    }

    public Iterator<AddressLookup> iterator(){
        return this.allLookups.iterator();
    }

    /***** Getters *******************************************************************************/

    public boolean getStandardizeOnly(){
        return this.standardizeOnly;
    }

    public boolean getIncludeInvalid(){
        return this.includeInvalid;
    }

    public Map<String, AddressLookup> getNamedLookups() {
        return this.namedLookups;
    }

    public AddressLookup get(String inputId){
        return this.namedLookups.get(inputId);
    }

    public AddressLookup get(int inputIndex) {
        return this.allLookups.get(inputIndex);
    }

    public Vector<AddressLookup> getAllLookups() {
        return this.allLookups;
    }

    /***** Setters *******************************************************************************/

    public void setStandardizeOnly(boolean newValue){
        this.standardizeOnly = newValue;
    }

    public void setIncludeInvalid(boolean newValue){
        this.includeInvalid = newValue;
    }
}
