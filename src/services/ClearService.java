package services;

import RequestResult.BasicSuccessResponse;
import RequestResult.Response;

/**
 * Deletes ALL data from the database, including user accounts, auth tokens, andgenerated person and event data.
 */
public class ClearService {
    /**
     * do the thing. the whole ting
     * @return
     */
    public Response clear(){
        return new BasicSuccessResponse();
    }
}
