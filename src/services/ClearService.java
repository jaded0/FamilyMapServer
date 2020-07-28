package services;

import DataAccess.*;
import RequestResult.BasicSuccessResponse;
import RequestResult.ErrorResponse;
import RequestResult.Response;

import java.sql.Connection;

/**
 * Deletes ALL data from the database, including user accounts, auth tokens, andgenerated person and event data.
 */
public class ClearService extends Service{
    /**
     * do the thing. the whole ting
     * @return
     */
    public Response clear(){
        // get a fresh database connection
        setUp();

        try {
            AuthTokenDAO tokenDAO = new AuthTokenDAO(conn);
            EventDAO eventDAO = new EventDAO(conn);
            PersonDAO personDAO = new PersonDAO(conn);
            UserDAO userDAO = new UserDAO(conn);
            tokenDAO.clear();
            eventDAO.clear();
            personDAO.clear();
            userDAO.clear();
            return new BasicSuccessResponse("clear succeeded");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorResponse("failed to clear");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
