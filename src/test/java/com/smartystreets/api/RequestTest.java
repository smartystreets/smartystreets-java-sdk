package com.smartystreets.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {
    @Test
    public void testNullNameQueryStringParameterNotAdded() throws Exception {
        this.assertQueryStringParameters(null, "value", "http://localhost/?");
    }

    @Test
    public void testEmptyNameQueryStringParameterNotAdded() throws Exception {
        this.assertQueryStringParameters("", "value", "http://localhost/?");
    }

    @Test
    public void testNullValueQueryStringParameterNotAdded() throws Exception {
        this.assertQueryStringParameters("name", null, "http://localhost/?");
    }

    @Test
    public void testEmptyValueQueryStringParameterIsAdded() throws Exception {
        this.assertQueryStringParameters("name", "", "http://localhost/?name=");
    }

    private void assertQueryStringParameters(String name, String value, String expected) throws Exception {
        Request request = new Request("http://localhost/?");

        request.putParameter(name, value);

        assertEquals(expected, request.getUrl());
    }

    @Test
    public void testMultipleQueryStringParameters() throws Exception {
        Request request = new Request("http://localhost/?");

        request.putParameter("name1", "value1");
        request.putParameter("name2", "value2");
        request.putParameter("name3", "value3");

        final String expected = "http://localhost/?name1=value1&name2=value2&name3=value3";
        assertEquals(expected, request.getUrl());
    }

    @Test
    public void testUrlEncodingOfQueryStringParameters() throws Exception {
        Request request = new Request("http://localhost/?");

        request.putParameter("name&", "value");
        request.putParameter("name1", "other !value$");

        final String expected = "http://localhost/?name%26=value&name1=other+%21value%24";

        assertEquals(expected, request.getUrl());
    }

    @Test
    public void testHeadersAddedToRequest() throws Exception {
        Request request = new Request("http://localhost/?");

        request.putHeader("header1", "value1");
        request.putHeader("header2", "value2");

        assertEquals("value1", request.getHeaders().get("header1"));
        assertEquals("value2", request.getHeaders().get("header2"));
    }

    @Test
    public void testGet() throws Exception {
        Request request = new Request("http://localhost/?");
        assertEquals("GET", request.getMethod());
        assertNull(request.getPayload());
    }

    @Test
    public void testPost() throws Exception {
        Request request = new Request("http://localhost/?");

        request.setPayload(new byte[]{0,1,2});
        byte[] actualPayload = request.getPayload();

        assertEquals("POST", request.getMethod());
        assertArrayEquals(new byte[]{0,1,2}, actualPayload);
    }

    @Test
    public void testUrlWithoutTrailingQuestionMark() {
        Request request = new Request("http://localhost/");

        request.putParameter("name1", "value1");
        request.putParameter("name2", "value2");
        request.putParameter("name3", "value3");

        final String expected = "http://localhost/?name1=value1&name2=value2&name3=value3";
        assertEquals(expected, request.getUrl());
    }
}