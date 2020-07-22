package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                //get the request URL from the exchange
                String urlPath = exchange.getRequestURI().toString();
                System.out.println("->" + urlPath + "<-");
                // define the default path
                if (urlPath == null || urlPath.equals("/") || urlPath.isEmpty()) {
                    urlPath = "/index.html";
                }

                String filePath = "web" + urlPath;

                File actualFile = new File(filePath);
                // return 404 not found if there's no such file
                if (!actualFile.exists()) exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                else {
                    // Start sending the HTTP response to the client, starting with
                    // the status code and any defined headers.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    // Now that the status code and headers have been sent to the client,
                    // next we send the page data in the response body
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(actualFile.toPath(), respBody);

                    // Close the output stream.  This is how Java knows we are done
                    // sending data and the response is complete/
                    respBody.close();
                }


                success = true;
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
        catch (IOException e) {
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
}
