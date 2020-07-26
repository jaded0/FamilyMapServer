package services;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import RequestResult.BasicSuccessResponse;
import RequestResult.ErrorResponse;
import RequestResult.LoadRequest;
import RequestResult.Response;
import model.Event;
import model.Person;
import model.User;

/**
 * Clears all data from the database (just like the /clear API),
 * and then loads the posted user, person, and event data into the database.
 */
public class LoadService extends Service{
    /**
     * do the loading.
     * @param request
     * @return
     */
    public Response load(LoadRequest request){
        try {
            db.clearTables();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorResponse("Error clearing tables for load.");
        }


        // insert users, persons, and events

        //count users, persons and events
        int users = 0; int persons = 0; int events = 0;
        UserDAO userDAO = new UserDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);
        try {
            for (User u :
                    request.getUsers()) {
                userDAO.insert(u);
                users++;
            }
            for (Person p :
                    request.getPersons()) {
                personDAO.insert(p);
                persons++;
            }
            for (Event e :
                    request.getEvents()) {
                eventDAO.insert(e);
                events++;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorResponse("Error inserting user, person, or event.");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }

        // return final positive status
        return new BasicSuccessResponse("Successfully added " + users + " users, " + persons + " persons, and " + events + " events to the database.");
    }
}
