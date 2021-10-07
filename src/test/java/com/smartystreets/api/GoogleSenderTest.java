package com.smartystreets.api;

import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GoogleSenderTest {
    private HttpRequest httpRequest;

    //region [ Request Building ]

    @Test
    public void testHttpRequestContainsCorrectHeaders() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockClient(200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");
        request.putHeader("X-name1", "value1");
        request.putHeader("X-name2", "value2");

        sender.send(request);

        Map<String, Object> headers = request.getHeaders();
        assertEquals("value1", headers.get("X-name1"));
        assertEquals("value2", headers.get("X-name2"));
    }

    @Test
    public void testHttpRequestContainsGetWhenAppropriate() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockClient(200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertArrayEquals("This is a GET response.".getBytes(), response.getPayload());
    }

    @Test
    public void testHttpRequestContainsPostWhenAppropriate() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockClient(200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        request.setPayload(new byte[0]);
        Response response = sender.send(request);

        assertArrayEquals("This is a POST response.".getBytes(), response.getPayload());
    }

//    @Test
//    public void testHttpRequestContainsCorrectContent() throws Exception {
//        GoogleSender sender = new GoogleSender(this.getMockClient(200));
//        Request request = new Request();
//        request.setUrlPrefix("http://localhost");
//
//        request.setPayload("This is the test content.".getBytes());
//        sender.send(request);
//
//        assertEquals("This is the test content.", this.httpRequest.bodyPublisher().toString());
//    }

    //endregion

    //region [ Response Packaging ]

    @Test
    public void testResponseContainsCorrectPayload() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockClient(200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertArrayEquals("This is a GET response.".getBytes(), response.getPayload());
    }

    @Test
    public void testResponseContainsStatusCode200OnSuccess() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockClient(200));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testResponseContainsStatusCode400WhenA400IsThrown() throws Exception {
        GoogleSender sender = new GoogleSender(this.getMockClient(400));
        Request request = new Request();
        request.setUrlPrefix("http://localhost");

        Response response = sender.send(request);

        assertEquals(400, response.getStatusCode());
    }

    //endregion

    //region [ Transports ]

    private HttpClient getMockClient(int statusCode) {
        final int status = statusCode;
        return new HttpClient() {
            @Override
            public Optional<CookieHandler> cookieHandler() {
                return Optional.empty();
            }

            @Override
            public Optional<Duration> connectTimeout() {
                return Optional.empty();
            }

            @Override
            public Redirect followRedirects() {
                return null;
            }

            @Override
            public Optional<ProxySelector> proxy() {
                return Optional.empty();
            }

            @Override
            public SSLContext sslContext() {
                return null;
            }

            @Override
            public SSLParameters sslParameters() {
                return null;
            }

            @Override
            public Optional<Authenticator> authenticator() {
                return Optional.empty();
            }

            @Override
            public Version version() {
                return null;
            }

            @Override
            public Optional<Executor> executor() {
                return Optional.empty();
            }

            @Override
            public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) throws IOException, InterruptedException {
                httpRequest = request;
                return new HttpResponse() {
                    @Override
                    public int statusCode() {
                        return status;
                    }

                    @Override
                    public HttpRequest request() {
                        return httpRequest;
                    }

                    @Override
                    public Optional<HttpResponse> previousResponse() {
                        return Optional.empty();
                    }

                    @Override
                    public HttpHeaders headers() {
                        return null;
                    }

                    @Override
                    public Object body() {
                        if (httpRequest.method().equals("GET"))
                            return "This is a GET response.";
                        else {
                            return "This is a POST response.";

                        }
                    }

                    @Override
                    public Optional<SSLSession> sslSession() {
                        return Optional.empty();
                    }

                    @Override
                    public URI uri() {
                        return null;
                    }

                    @Override
                    public Version version() {
                        return null;
                    }
                };
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
                return null;
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler, HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
                return null;
            }
        };
    }

    //endregion
}