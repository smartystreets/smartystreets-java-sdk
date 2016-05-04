package com.smartystreets.api.us_street;

import java.util.*;

public class Batch {

    private final int MAX_BATCH_SIZE = 100;
    private Map<String, AddressLookup> namedLookups;
    private Vector<AddressLookup> allLookups;
    private boolean standardizeOnly, includeInvalid;
 //   private javax.net.Request request;

    public Batch(){
        this.standardizeOnly = false;
        this.includeInvalid = false;
        this.namedLookups = new LinkedHashMap<>();
        this.allLookups = new Vector<>();
    }

    public boolean add(AddressLookup newAddress){
        if (this.namedLookups.size() >= MAX_BATCH_SIZE) {
            return false;
        }

        String key = newAddress.getInputId();

        if (key != null)
            this.namedLookups.put(key, newAddress);

        this.allLookups.add(newAddress);
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
        this.namedLookups.clear();
        this.allLookups.clear();
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

    public Map<String, AddressLookup> getNamedLookups() {
        return this.namedLookups;
    }

    public AddressLookup get(String inputId){
        return this.namedLookups.get(inputId);
    }

    public AddressLookup get(int index) {
        return this.allLookups.get(index);
    }

    public Iterator<AddressLookup> iterator(){
        return this.allLookups.iterator();
    }

    public int size() {
        return this.allLookups.size();
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
