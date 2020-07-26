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
     * @param personID
     * @return
     */
    public Response singlePerson(String personID){
        PersonDAO personDAO = new PersonDAO(conn);
        try {
            Person person = personDAO.retrieve(personID);

            return new PersonSuccessResponse(person.getAssociatedUsername(),
                    person.getPersonID(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getGender(),
                    person.getFatherID(),
                    person.getMotherID(),
                    person.getSpouseID());
        } catch (DataAccessException e) {
            e.printStackTrace();
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
     * @param authtoken
     * @return
     */
    public Response allPersons(String authtoken){
        PersonDAO personDAO = new PersonDAO(conn);
        // get username for authtoken
        AuthTokenDAO tokenDAO = new AuthTokenDAO(conn);
        try {
            String username = tokenDAO.getUsernameForAuthtoken(authtoken);
            ArrayList<Person> persons = personDAO.getPersonsForUsername(username);
            PersonsSuccessResponse result = new PersonsSuccessResponse();
            result.setMembers(persons);

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
