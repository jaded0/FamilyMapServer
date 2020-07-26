package Handlers;

import RequestResult.LoadRequest;
import RequestResult.Response;
import com.google.gson.Gson;
import services.LoadService;

public class LoadHandler extends Handler{
    public LoadHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected Response workWithService(String requestURI, String reqData) {
        // Display/log the request JSON data
        System.out.println(reqData);

        // Create a request out of the body
        Gson gson = new Gson();
        LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
        // send that request to the service
        LoadService service = new LoadService();
        // also, get back a response
        return service.load(request);
    }
}
