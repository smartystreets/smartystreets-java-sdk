package com.smartystreets.api.us_zipcode;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {
    @Test
    public void isValid() throws Exception {

        /**Case 1: test to make sure that isValid returns true when input is valid*/
        Result result = new Result();

        boolean isValid = result.isValid();

        assertEquals(true, isValid);

        //TODO: Case 2
//        /**Case 2: test to make sure that isValid returns false when input is not valid*/
//        MockLowLevelHttpResponse mock = new MockLowLevelHttpResponse().setContent("{\"status\":\"invalid_zipcode\",\"reason\":\"Invalid ZIP Code.\"}");
//
//
//        HttpResponse response = new HttpResponse(new HttpRequest(new MockHttpTransport(), "GET"), mock);
//
//        result.setStatus("invalid_zipcode");
//        result.setReason("invalid_reason");
//
//        isValid = result.isValid();
//
//        assertEquals(false, isValid);
    }

}