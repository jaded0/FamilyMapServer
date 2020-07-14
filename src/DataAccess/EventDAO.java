package DataAccess;

import model.Event;
import model.User;

import java.util.ArrayList;

/**
 * all operations related to an event
 */
public class EventDAO {
    /**
     * get an event by their id
     * @param id
     * @return
     */
    public Event getPerson(String id){
        return new Event();
    }

    /**
     * gets all the events
     * @return
     */
    public ArrayList<Event> getPersons(){
        return new ArrayList<Event>();
    }

    /**
     * fill event data for this user.
     * @param user
     */
    public void fill(User user){

    }
}
