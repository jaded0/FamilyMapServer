package Handlers;

import RequestResult.LoginRequest;
import RequestResult.LoginRequest;
import RequestResult.Request;
import RequestResult.Response;
import com.google.gson.Gson;
import services.LoginService;
import services.LoginService;

public class LoginHandler extends Handler{
    public LoginHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected Response workWithService(String reqData) {
        // Display/log the request JSON data
        System.out.println(reqData);

        // Create a request out of the body
        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(reqData, LoginRequest.class);
        // send that request to the service
        LoginService service = new LoginService();
        // also, get back a response
        return service.login(request);
    }
}
