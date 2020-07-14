package services;

import RequestResult.PersonSuccessResponse;
import RequestResult.PersonsSuccessResponse;
import RequestResult.Response;

/**
 * Returns person object or all people object
 */
public class PersonService {
    /**
     * Returns the single Person object with the specified ID.
     * @param personID
     * @return
     */
    public Response singlePerson(String personID){
        return new PersonSuccessResponse();
    }

    /**
     * Returns ALL family members of the current user.
     * The current user is determined from the provided auth token.
     * @param personID
     * @return
     */
    public Response allPersons(String personID){
        return new PersonsSuccessResponse();
    }
}
