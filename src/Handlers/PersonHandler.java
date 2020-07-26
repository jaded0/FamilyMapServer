package Handlers;

import RequestResult.Response;
import com.google.gson.Gson;
import services.FillService;
import services.PersonService;

public class PersonHandler extends Handler{
    public PersonHandler() {
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
        PersonService service = new PersonService();
        if (commands.length>2)
            return service.singlePerson(commands[2]);
        else
            return service.allPersons(authToken);
    }
}
