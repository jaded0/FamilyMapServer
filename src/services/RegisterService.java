package services;

import RequestResult.RegisterRequest;
import RequestResult.RegisterSuccessResponse;
import RequestResult.Response;

/**
 * Creates a new user account, generates 4 generations of ancestor data for the new user,
 * logs the user in, and returns an auth token.
 */
public class RegisterService {

    /**
     * the big method for this class. does all the stuff
     * @param request
     * @return
     */
    public Response register(RegisterRequest request){
        return new RegisterSuccessResponse();
    }
}
