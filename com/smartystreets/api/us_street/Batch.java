package com.smartystreets.api.us_street;

/**
 * Created by Neo on 4/19/16.
 */

import java.util.*;

public class Batch {

    private final int MAX_BATCH_SIZE = 100;
    private Map<String, AddressLookup> lookups;
    private boolean standardizeOnly, includeInvalid;
 //   private javax.net.Request request;

    public Batch(){
        this.standardizeOnly = false;
        this.includeInvalid = false;
        this.lookups = new LinkedHashMap<>();
    }

    public boolean add(AddressLookup newAddress){
        if (this.lookups.size() >= MAX_BATCH_SIZE) {
            return false;
        }

        String key = newAddress.getInputId();

        if (key == null)                                // Use index (as a string) if no input id is given
            key = Integer.toString(this.lookups.size());

        this.lookups.put(key, newAddress);
        return true;

    }

    public int add(Collection<AddressLookup> newAddresses){
        int numAdded = 0;

        for (AddressLookup current : newAddresses) {
            if (this.add(current))
                numAdded++;
            else return numAdded;
        }

        return numAdded;
    }

    public void clear(){
        this.lookups.clear();
    }

    public void reset() {
        this.clear();
        this.standardizeOnly = false;
        this.includeInvalid = false;
    }

    /***** Getters *******************************************************************************/

    public boolean getStandardizeOnly(){
        return this.standardizeOnly;
    }

    public boolean getIncludeInvalid(){
        return this.includeInvalid;
    }

    public Map getLookups() {
        return this.lookups;
    }

    public AddressLookup get(String inputId){
        return this.lookups.get(inputId);
    }

    public AddressLookup get(int index){
        return (AddressLookup) this.lookups.values().toArray()[index];
    }

    public Iterator<AddressLookup> iterator(){
        return this.lookups.values().iterator();
    }

    public int size() {
        return this.lookups.size();
    }

    /***** Setters *******************************************************************************/

    public void setStandardizeOnly(boolean newValue){
        this.standardizeOnly = newValue;
    }

    public void setIncludeInvalid(boolean newValue){
        this.includeInvalid = newValue;
    }
}
