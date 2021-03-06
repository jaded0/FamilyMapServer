package Handlers;

import RequestResult.RegisterRequest;
import RequestResult.Response;
import com.google.gson.Gson;
import services.RegisterService;

import java.net.URI;

public class RegisterHandler extends Handler{
    public RegisterHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected Response workWithService(String requestURI, String reqData) {
        // Display/log the request JSON data
        System.out.println(reqData);

        // Create a request out of the body
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);
        // send that request to the service
        RegisterService service = new RegisterService();
        // also, get back a response
        return service.register(request);
    }
}
