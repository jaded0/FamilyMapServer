package services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.PersonDAO;
import RequestResult.ErrorResponse;
import RequestResult.PersonSuccessResponse;
import RequestResult.PersonsSuccessResponse;
import RequestResult.Response;
import model.Person;

import java.util.ArrayList;

/**
 * Returns person object or all people object
 */
public class PersonService extends Service{

    /**
     * Returns the single Person object with the specified ID.
     * @param personID Unique person identifier in order to find the item.
     * @param authtoken Used to verify that the person data belongs to the user.
     * @return
     */
    public Response singlePerson(String personID, String authtoken){
        PersonDAO personDAO = new PersonDAO(conn);
        try {
            Person person = personDAO.retrieve(personID);

            // validate that the user has the rights to get this person
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
            if (!authTokenDAO.getUsernameForAuthtoken(authtoken).equals(person.getAssociatedUsername()))
                return new ErrorResponse("Error you arecertainlynot authorized to get that person info");
            return new PersonSuccessResponse(person.getAssociatedUsername(),
                    person.getPersonID(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getGender(),
                    person.getFatherID(),
                    person.getMotherID(),
                    person.getSpouseID());
        } catch (DataAccessException e) {
//            e.printStackTrace();
            return new ErrorResponse("Error while getting single person");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }

    /**
     * Returns ALL family members of the current user.
     * The current user is determined from the provided auth token.
     * @param authtoken Used to ascertain the user from which to pull the person data out of.
     * @return
     */
    public Response allPersons(String authtoken){
        PersonDAO personDAO = new PersonDAO(conn);
        // get username for authtoken
        AuthTokenDAO tokenDAO = new AuthTokenDAO(conn);
        try {
            // find username, then person list by username, then place that into the result and send it out
            String username = tokenDAO.getUsernameForAuthtoken(authtoken);
            ArrayList<Person> persons = personDAO.getPersonsForUsername(username);
            PersonsSuccessResponse result = new PersonsSuccessResponse();
            result.setData(persons);

            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorResponse("Error while getting people");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }
}
