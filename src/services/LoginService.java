package services;

import RequestResult.LoginRequest;
import RequestResult.LoginSuccessResponse;
import RequestResult.Response;

/**
 * Logs in the user and returns an auth token.
 */
public class LoginService {
    /**
     * the biggo. stuff happens here
     * @param request
     * @return
     */
    public Response login(LoginRequest request){
        return new LoginSuccessResponse();
    }
}
