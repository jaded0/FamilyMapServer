package services;

import RequestResult.EventSuccessResponse;
import RequestResult.EventsSuccessResponse;
import RequestResult.Response;

/**
 * Returns person object or all people object
 */
public class EventService {
    /**
     * Returns the single Event object with the specified ID.
     * @param personID
     * @return
     */
    public Response singleEvent(String personID){
        return new EventSuccessResponse();
    }

    /**
     * Returns ALL events related to the current user.
     * The current user is determined from the provided auth token.
     * @param personID
     * @return
     */
    public Response allEvents(String personID){
        return new EventsSuccessResponse();
    }
}
