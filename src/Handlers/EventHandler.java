package Handlers;

import RequestResult.Response;
import services.EventService;

public class EventHandler extends Handler{
    public EventHandler() {
        getOrPost = "get";
        authenticate = true;
    }
    @Override
    protected Response workWithService(String requestURI, String reqData) {
        // Display/log the request JSON data
        System.out.println(reqData);

        // take the personID out of the URI
        String[] commands = requestURI.split("/");
        // get back a response from the service
        EventService service = new EventService();
        if (commands.length>2)
            return service.singleEvent(commands[2], authToken);
        else
            return service.allEvents(authToken);
    }
}
