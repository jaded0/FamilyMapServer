package services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import RequestResult.*;
import model.User;

import java.util.UUID;

/**
 * Logs in the user and returns an auth token.
 */
public class LoginService extends Service{
    /**
     * the biggo. stuff happens here
     * @param request
     * @return
     */
    public Response login(LoginRequest request){
        setUp();
        // make sure setup went well
        if (conn == null)
            return new ErrorResponse("Error getting the database");
        // set up userDAO
        try {
            //pass the connection to the DAO
            UserDAO userDAO = new UserDAO(conn);
            // retrieve the proper person
            User user = userDAO.retrieve(request.getUserName());

            if(user.getPassword().equals(request.getPassword())) return new LoginSuccessResponse(new AuthTokenDAO(conn).newAuthToken(user).getToken(),
                    user.getUsername(), user.getPersonID());
            else return new ErrorResponse("Error wrong password");
        } catch (DataAccessException e) {
            //e.printStackTrace();
            return new ErrorResponse("error logging in the user or getting the database");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }

        }
    }
}
