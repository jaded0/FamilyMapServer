package RequestResult;

/**
 * Returns event object(s)
 */
public class EventService {
    /**
     * Returns the single Event object with the specified ID.
     * @param eventID
     * @return
     */
    public Response singleEvent(String eventID){
        return new EventSuccessResponse();
    }

    /**
     * ​Returns ALL events for ALL family members of the current user.
     * The current user is determined from the provided auth token.
     * @param eventID
     * @return
     */
    public Response allEvents(String eventID){
        return new EventsSuccessResponse();
    }
}
