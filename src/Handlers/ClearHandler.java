package Handlers;

import RequestResult.LoginRequest;
import RequestResult.Response;
import com.google.gson.Gson;
import services.ClearService;
import services.LoginService;

public class ClearHandler extends Handler{
    public ClearHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected Response workWithService(String reqData) {
        // Display/log the request JSON data
        System.out.println(reqData);

        // send that request to the service
        ClearService service = new ClearService();
        // also, get back a response
        return service.clear();
    }
}
