package com.smartystreets.api;

import com.smartystreets.api.exceptions.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpSender implements Sender {
    private int maxTimeOut;

    public HttpSender() {
        this.maxTimeOut = 10000;
    }

    public HttpSender(int maxTimeout) {
        this.maxTimeOut = maxTimeout;
    }

    public Response send(Request request) throws SmartyException, IOException{
        Response response = new Response();

        //open the url connection
        HttpsURLConnection connection = (HttpsURLConnection) new URL(request.getUrlString()).openConnection();
        connection.setConnectTimeout(this.maxTimeOut);

        try {
            //set headers
            Map<String, String> headers = request.getHeaders();
            for (String headerName : headers.keySet()) {
                connection.setRequestProperty(headerName, headers.get(headerName));
            }

            System.out.println(connection.getRequestProperty("X-Include-Invalid"));


            //write the bytes of JSON payload to the output stream
            String CHARSET = "UTF-8";
            if (request.getJsonPayload() != null) {
                connection.setDoOutput(true);
                //create output stream
                OutputStream output = connection.getOutputStream();
                output.write(request.getJsonPayload().getBytes(CHARSET));
                output.close();
            }

            //create an input stream for the response
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, CHARSET));

            String rawJSON = reader.readLine();

            reader.close();
            input.close();
            connection.disconnect();

            //build response object from the input stream
            response.setStatusCode(connection.getResponseCode());
            response.setStatus(connection.getResponseMessage());
            response.setRawJSON(rawJSON);

            Map<String, List<String>> rawResponseHeaders = connection.getHeaderFields();
            Map<String, String> responseHeaders = new HashMap<>();

            for (String headerName : rawResponseHeaders.keySet()) {
                responseHeaders.put(headerName, rawResponseHeaders.get(headerName).get(0));
            }

            response.setHeaders(responseHeaders);

        }
        catch(IOException ex) {
            switch (connection.getResponseCode()) {
                case 400: throw new BadRequestException("Bad Request (Malformed Payload): A GET request lacked a street field or the request body of a POST request contained malformed JSON.");
                case 401: throw new UnauthorizedException("Unauthorized: The credentials were provided incorrectly or did not match any existing, active credentials.");
                case 402: throw new PaymentRequiredException("Payment Required: There is no active subscription for the account associated with the credentials submitted with the request.");
                case 413: throw new RequestEntityTooLargeException("Request Entity Too Large: The maximum size for a request body to this API is 32K (32,768 bytes).");
                case 429: throw new TooManyRequestsException("Too Many Requests: When using public \"website key\" authentication, we restrict the number of requests coming from a given source over too short of a time.");
                default: throw ex;
            }
        }

        return response;
    }
}
