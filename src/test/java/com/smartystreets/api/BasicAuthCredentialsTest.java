package com.smartystreets.api;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

public class BasicAuthCredentialsTest {

    @Test
    public void testNewBasicAuthCredentialWithValidCredentials() {
        BasicAuthCredentials cred = new BasicAuthCredentials("testID", "testToken");
        assertNotNull(cred);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBasicAuthCredentialWithEmptyAuthID() {
        new BasicAuthCredentials("", "testToken");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBasicAuthCredentialWithEmptyAuthToken() {
        new BasicAuthCredentials("testID", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBasicAuthCredentialWithBothEmpty() {
        new BasicAuthCredentials("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBasicAuthCredentialWithNullAuthID() {
        new BasicAuthCredentials(null, "testToken");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBasicAuthCredentialWithNullAuthToken() {
        new BasicAuthCredentials("testID", null);
    }

    @Test
    public void testNewBasicAuthCredentialWithSpecialCharacters() {
        BasicAuthCredentials cred = new BasicAuthCredentials("test@id#123", "token!@#$%^&*()");
        assertNotNull(cred);
    }

    @Test
    public void testSignWithValidCredentials() {
        BasicAuthCredentials cred = new BasicAuthCredentials("myID", "myToken");
        Request request = new Request();
        request.setUrlPrefix("https://us-street.api.smarty.com/street-address?");

        cred.sign(request);

        String expectedEncoded = Base64.getEncoder().encodeToString("myID:myToken".getBytes());
        assertEquals("Basic " + expectedEncoded, request.getHeaders().get("Authorization"));
    }

    @Test
    public void testSignWithPasswordContainingColon() {
        BasicAuthCredentials cred = new BasicAuthCredentials("validUserID", "password:with:colons");
        Request request = new Request();
        request.setUrlPrefix("https://us-street.api.smarty.com/street-address?");

        cred.sign(request);

        String expectedEncoded = Base64.getEncoder().encodeToString("validUserID:password:with:colons".getBytes());
        assertEquals("Basic " + expectedEncoded, request.getHeaders().get("Authorization"));
    }

    @Test
    public void testSignWithSpecialCharacters() {
        BasicAuthCredentials cred = new BasicAuthCredentials("user@domain.com", "p@ssw0rd!");
        Request request = new Request();
        request.setUrlPrefix("https://us-street.api.smarty.com/street-address?");

        cred.sign(request);

        String expectedEncoded = Base64.getEncoder().encodeToString("user@domain.com:p@ssw0rd!".getBytes());
        assertEquals("Basic " + expectedEncoded, request.getHeaders().get("Authorization"));
    }

    @Test
    public void testSignWithUnicodeCharacters() {
        BasicAuthCredentials cred = new BasicAuthCredentials("用户", "密码");
        Request request = new Request();
        request.setUrlPrefix("https://us-street.api.smarty.com/street-address?");

        cred.sign(request);

        String expectedEncoded = Base64.getEncoder().encodeToString("用户:密码".getBytes());
        assertEquals("Basic " + expectedEncoded, request.getHeaders().get("Authorization"));
    }

    @Test
    public void testSignDoesNotAddQueryParameters() {
        BasicAuthCredentials cred = new BasicAuthCredentials("myID", "myToken");
        Request request = new Request();
        request.setUrlPrefix("https://us-street.api.smarty.com/street-address?");

        cred.sign(request);

        String url = request.getUrl();
        assertFalse("URL should not contain auth-id parameter", url.contains("auth-id"));
        assertFalse("URL should not contain auth-token parameter", url.contains("auth-token"));
    }
}
