package Handlers;

import RequestResult.Response;
import services.ClearService;

import java.net.URI;

public class ClearHandler extends Handler{
    public ClearHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected Response workWithService(String requestURI, String reqData) {
        // Display/log the request JSON data
        System.out.println(reqData);

        // send that request to the service
        ClearService service = new ClearService();
        // also, get back a response
        return service.clear();
    }
}
