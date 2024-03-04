package com.smartystreets.api;

import com.smartystreets.api.exceptions.SmartyException;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.IOException;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class SmartySender implements Sender {
    private final int maxTimeOut;
    private final OkHttpClient client;

    SmartySender(int maxTimeOut) {
        this.maxTimeOut = maxTimeOut;
        this.client = new OkHttpClient.Builder()
                .writeTimeout(this.maxTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(this.maxTimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(this.maxTimeOut, TimeUnit.MILLISECONDS)
                .build();
    }

    SmartySender(int maxTimeOut, Proxy proxy) {
        this.maxTimeOut = maxTimeOut;
        this.client = new OkHttpClient.Builder()
                .proxy(proxy)
                .writeTimeout(this.maxTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(this.maxTimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(this.maxTimeOut, TimeUnit.MILLISECONDS)
                .build();
    }

    SmartySender(int maxTimeOut, OkHttpClient client) {
        this.maxTimeOut = maxTimeOut;
        this.client = client;
    }

    public Response send(Request smartyRequest) throws SmartyException, IOException {
        okhttp3.Request httpRequest = buildHttpRequest(smartyRequest);

        try (okhttp3.Response httpResponse = client.newCall(httpRequest).execute()){
            int statusCode = httpResponse.code();
            if (statusCode == 429){
                return new TooManyRequestsResponse(httpResponse.headers(), statusCode, httpResponse.body().bytes());
            }
            return new Response(statusCode, httpResponse.body().bytes());
        } catch(IOException ex) {
            return new Response(ex.hashCode(), new byte[0]);
        }
    }

    private okhttp3.Request buildHttpRequest(Request smartyRequest) throws IOException {

        Map<String, Object> headers = smartyRequest.getHeaders();
        Headers.Builder headersBuilder = new Headers.Builder();
        for (String headerName : headers.keySet()) {
            headersBuilder.add(headerName, headers.get(headerName).toString());
        }

        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
                .url(smartyRequest.getUrl())
                .headers(headersBuilder.build());

        if (smartyRequest.getMethod().equals("GET")) {
            return requestBuilder
                    .get()
                    .build();
        }

        return requestBuilder.post(RequestBody.create(smartyRequest.getPayload())).build();
    }

    private Response buildResponse(okhttp3.Response httpResponse) {
        int statusCode = httpResponse.code();
        if (statusCode == 429){
            return new TooManyRequestsResponse(httpResponse.headers(), statusCode, httpResponse.body().toString().getBytes());
        }
        return new Response(statusCode, httpResponse.body().toString().getBytes());
    }

    static void enableLogging() {
        Logger logger = Logger.getLogger(OkHttpClient.class.getName());
        logger.setLevel(Level.ALL);
        logger.addHandler(new Handler() {
            @Override
            public void close() throws SecurityException {
            }

            @Override
            public void flush() {
            }

            @Override
            public void publish (LogRecord record) {
                // default ConsoleHandler will print >= INFO to System.err
                if (record.getLevel().intValue() < Level.INFO.intValue())
                    System.out.println(record.getMessage());
            }
        });
    }
}
