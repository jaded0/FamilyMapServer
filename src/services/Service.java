package services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import RequestResult.ErrorResponse;

import java.sql.Connection;

/**
 * A base class to set up for all services
 */
public abstract class Service {
    protected Database db;
    protected Connection conn = null;
    Service() {
        setUp();
    }
    protected void setUp(){
        //lets create a new database
        db = new Database();
        //Here, we'll open the connection in preparation for the register dao to use it.
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
