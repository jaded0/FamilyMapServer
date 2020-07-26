package services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import RequestResult.RegisterRequest;
import RequestResult.RegisterSuccessResponse;
import RequestResult.Response;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    @Test
    void register() throws DataAccessException {
        Database db = new Database();
        UserDAO dao = new UserDAO(db.getConnection());
        dao.clear();
        db.closeConnection(true);
        RegisterService service = new RegisterService();
        RegisterRequest request = new RegisterRequest("jaden", "password", "email@mail.com", "firstname", "lastname", "m");
        Response response = service.register(request);
        assertTrue(response.isSuccess());
        if (response.isSuccess()) {
            RegisterSuccessResponse realResponse = (RegisterSuccessResponse) response;
            User correctUser = new User("jaden", "password", "email@mail.com", "firstname", "lastname", "m", realResponse.getPersonID());
            db = new Database();
            dao = new UserDAO(db.getConnection());
            assertEquals(correctUser, dao.retrieve(correctUser.getUserName()));
            db.closeConnection(false);
        }
    }

    @Test
    void registerFails() throws DataAccessException {
        Database db = new Database();
        UserDAO dao = new UserDAO(db.getConnection());
        dao.clear();
        db.closeConnection(true);
        RegisterService service = new RegisterService();
        RegisterRequest request = new RegisterRequest("username", "password", "email@mail.com", "firstname", "lastname", "m");
        service.register(request);
        Response response = service.register(request);
        assertFalse(response.isSuccess());
    }
}