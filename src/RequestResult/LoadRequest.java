package RequestResult;

import java.util.ArrayList;

/**
 * Clears all data from the database (just like the /clear API),
 * and then loads the posted user, person, and event data into the database.
 */
public class LoadRequest {
    /**
     * Array of User objects
     */
    ArrayList<String> users;
    /**
     * Array of Person objects
     */
    ArrayList<String> persons;
    /**
     * Array of event objects
     */
    ArrayList<String> events;
}
