package services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import RequestResult.ErrorResponse;
import RequestResult.EventSuccessResponse;
import RequestResult.EventsSuccessResponse;
import RequestResult.Response;
import model.Event;

import java.util.ArrayList;

/**
 * Returns event object or all people object
 */
public class EventService extends Service{

    /**
     * Returns the single Event object with the specified ID.
     * @param eventID
     * @return
     */
    public Response singleEvent(String eventID, String authtoken){
        EventDAO eventDAO = new EventDAO(conn);
        try {
            // get the event
            Event event = eventDAO.retrieve(eventID);

            // validate that the user has the rights to get this person
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
            if (!authTokenDAO.getUsernameForAuthtoken(authtoken).equals(event.getAssociatedUsername()))
                return new ErrorResponse("Error you arecertainlynot authorized to get that event info");

            return new EventSuccessResponse(
                    event.getAssociatedUsername(),
                    event.getEventID(),
                    event.getPersonID(),
                    event.getLatitude(),
                    event.getLongitude(),
                    event.getCountry(),
                    event.getCity(),
                    event.getEventType(),
                    event.getYear());
        } catch (DataAccessException e) {
//            e.printStackTrace();
            return new ErrorResponse("Error while getting single event");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }

    /**
     * Returns ALL events of the specified user.
     * The current user is determined from the provided auth token.
     * @param authtoken used to ascertain the user's identity.
     * @return
     */
    public Response allEvents(String authtoken){
        EventDAO eventDAO = new EventDAO(conn);
        // get username for authtoken
        AuthTokenDAO tokenDAO = new AuthTokenDAO(conn);
        try {
            String username = tokenDAO.getUsernameForAuthtoken(authtoken);
            ArrayList<Event> events = eventDAO.getEventsForUsername(username);
            EventsSuccessResponse result = new EventsSuccessResponse();
            result.setMembers(events);

            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorResponse("Error while getting events");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }
}
