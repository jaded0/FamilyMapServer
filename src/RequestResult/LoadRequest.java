package RequestResult;

import model.Event;
import model.Person;
import model.User;

import java.util.ArrayList;

/**
 * Clears all data from the database (just like the /clear API),
 * and then loads the posted user, person, and event data into the database.
 */
public class LoadRequest extends Request{
    /**
     * Array of User objects
     */
    ArrayList<User> users;
    /**
     * Array of Person objects
     */
    ArrayList<Person> persons;
    /**
     * Array of event objects
     */
    ArrayList<Event> events;

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
