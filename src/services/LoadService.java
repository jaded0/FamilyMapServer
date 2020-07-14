package services;

import RequestResult.BasicSuccessResponse;
import RequestResult.LoadRequest;
import RequestResult.Response;

/**
 * Clears all data from the database (just like the /clear API),
 * and then loads the posted user, person, and event data into the database.
 */
public class LoadService {
    /**
     * do the loading.
     * @param request
     * @return
     */
    public Response load(LoadRequest request){
        return new BasicSuccessResponse();
    }
}
