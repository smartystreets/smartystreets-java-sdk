package com.smartystreets.api.us_zipcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {
    @Test
    public void isValid() throws Exception {

        /**Case 1: test to make sure that isValid returns true when input is valid*/
        Result result = new Result();

        boolean isValid = result.isValid();

        assertEquals(true, isValid);

        /**Case 2: test to make sure that isValid returns false when input is not valid*/
        result.status = "invalid_zipcode";
        result.reason = "invalid_reason";

        isValid = result.isValid();

        assertEquals(false, isValid);
    }

}