package Handlers;

import RequestResult.Response;
import com.google.gson.Gson;
import services.FillService;

import java.net.URI;

public class FillHandler extends Handler{
    public FillHandler() {
        getOrPost = "post";
        authenticate = false;
    }
    @Override
    protected Response workWithService(String requestURI, String reqData) {
        // Display/log the request JSON data
        System.out.println(reqData);

        // take the username and generations out of the URI
        String[] commands = requestURI.split("/");
        String username = commands[2];
        int generations = Integer.parseInt(commands[3]);


        // Create a request out of the body
        Gson gson = new Gson();
        // send that request to the service
        FillService service = new FillService();
        // also, get back a response

        return service.fill(username, generations);
    }
}
