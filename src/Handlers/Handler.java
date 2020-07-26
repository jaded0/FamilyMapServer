package Handlers;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import RequestResult.Response;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;

import com.google.gson.*;
import model.AuthToken;

public abstract class Handler implements HttpHandler {
    // this is where the individual stuff will happen
    abstract protected Response workWithService(String requestURI, String reqData);
    String getOrPost;
    Boolean authenticate;
    String authToken;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            // check whether it's get or post, and if that's right.
            if (exchange.getRequestMethod().toLowerCase().equals(getOrPost)) {
                // only does this authorization if necessary
                if (authenticate) {
                    // Get the HTTP request headers
                    Headers reqHeaders = exchange.getRequestHeaders();
                    // Check to see if an "Authorization" header is present
                    if (reqHeaders.containsKey("Authorization")) {

                        // Extract the auth token from the "Authorization" header
                        authToken = reqHeaders.getFirst("Authorization");
                        // Verify that the auth token exists
                        // sloppily sets up the database, connection, dao, and token object to do so.
                        if (new AuthTokenDAO(new Database().getConnection()).verify(new AuthToken(authToken))) {
                            success = translateJson(exchange);
                        }
                    }
                }
                else { // if you don't need to authenticate
                    success = translateJson(exchange);
                }

            }
            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                // Since the client request was invalid, they will not receive the
                // list of games, so we close the response body output stream,
                // indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

    private boolean translateJson(HttpExchange exchange) throws IOException {
        boolean success = false;
        // Extract the JSON string from the HTTP request body

        // Get the request body input stream
        InputStream reqBody = exchange.getRequestBody();
        // Read JSON string from the input stream
        String reqData = readString(reqBody);

        Gson gson = new Gson();
        // also, get back a response
        Response resp = workWithService(exchange.getRequestURI().toString(), reqData);
        // turn that response into json string
        String jsonRespStr = gson.toJson(resp);

        // if response fails, bail, turn tail.
        if (resp.isSuccess()) {

            // Start sending the HTTP response to the client, starting with
            // the status code and any defined headers.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            // Get the response body output stream.
            OutputStream respBody = exchange.getResponseBody();
            // Write the JSON string to the output stream.
            writeString(jsonRespStr, respBody);
            // Close the output stream.  This is how Java knows we are done
            // sending data and the response is complete/
            respBody.close();

            success = true;
        }
        else { // pass back the error message
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            // Get the response body output stream.
            OutputStream respBody = exchange.getResponseBody();
            // Write the JSON string to the output stream.
            writeString(jsonRespStr, respBody);
            // Close the output stream.  This is how Java knows we are done
            // sending data and the response is complete/
            respBody.close();
            exchange.getResponseBody().close();
            success = true;
        }
        return success;
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
