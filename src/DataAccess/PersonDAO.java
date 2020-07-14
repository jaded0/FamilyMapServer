package DataAccess;

import model.Person;
import model.User;

import java.util.ArrayList;

/**
 * all operations related to a person object
 */
public class PersonDAO {
    /**
     * get a person by their id
     * @param id
     * @return
     */
    public Person getPerson(String id){
        return new Person();
    }

    /**
     * gets all the people
     * @return
     */
    public ArrayList<Person> getPersons(){
        return new ArrayList<Person>();
    }

    /**
     * fill generational data for this user.
     * @param user
     */
    public void fill(User user){

    }
}
