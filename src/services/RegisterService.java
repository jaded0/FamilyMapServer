package services;

import DataAccess.*;
import RequestResult.ErrorResponse;
import RequestResult.RegisterRequest;
import RequestResult.RegisterSuccessResponse;
import RequestResult.Response;
import model.User;

import java.sql.Connection;
import java.util.UUID;

/**
 * Creates a new user account, generates 4 generations of ancestor data for the new user,
 * logs the user in, and returns an auth token.
 */
public class RegisterService extends Service {

    /**
     * the big method for this class. does all the stuff
     * @param request
     * @return
     */
    public Response register(RegisterRequest request){
        setUp();
        // make sure setup went well
        if (conn == null)
            return new ErrorResponse("error registering the user or getting the database");
        // set up userDAO
        // make user with random id
        PersonDAO personDAO = new PersonDAO(conn);
        String id = personDAO.generateID();
        User ourUser = new User(request.getUserName(), request.getPassword(), request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getGender(), id);
        try {
            //pass the connection to the DAO
            UserDAO userDAO = new UserDAO(conn);
            userDAO.insert(ourUser);

            return new RegisterSuccessResponse(new AuthTokenDAO(conn).newAuthToken(ourUser).getToken(), ourUser.getUsername(), id);
        } catch (DataAccessException e) {
            //e.printStackTrace();
            return new ErrorResponse("error registering the user or getting the database");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }

    }
}
