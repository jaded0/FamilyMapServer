package services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import RequestResult.*;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    @Test
    void login() throws DataAccessException {
        Database db = new Database();
        UserDAO dao = new UserDAO(db.getConnection());
        dao.clear();
        User correctUser = new User("username", "password", "email@mail.com", "firstname", "lastname", "m", "personid");
        User other = new User("otherusername", "password", "email@mail.com", "firstname", "lastname", "m", "otherpersonid");
        dao.insert(correctUser);
        dao.insert(other);
        db.closeConnection(true);
        LoginService service = new LoginService();
        LoginRequest request = new LoginRequest(correctUser.getUsername(), correctUser.getPassword());
        Response response = service.login(request);
        assertTrue(response.isSuccess());
        if(response.isSuccess()) {
            LoginSuccessResponse realResponse = (LoginSuccessResponse) response;
            assertEquals(correctUser.getUsername(), realResponse.getUserName());
        }
        LoginRequest secondrequest = new LoginRequest(other.getUsername(), other.getPassword());
        Response secondresponse = service.login(secondrequest);
        assertTrue(secondresponse.isSuccess());
        if(secondresponse.isSuccess()) {
            LoginSuccessResponse realResponse = (LoginSuccessResponse) secondresponse;
            assertEquals(other.getUsername(), realResponse.getUserName());
        }
    }

    @Test
    void loginFails() throws DataAccessException {
        Database db = new Database();
        UserDAO dao = new UserDAO(db.getConnection());
        dao.clear();
        User correctUser = new User("username", "password", "email@mail.com", "firstname", "lastname", "m", "personid");
        dao.insert(correctUser);
        db.closeConnection(true);
        LoginService service = new LoginService();
        LoginRequest request = new LoginRequest(correctUser.getUsername(), "wrongpassword");
        LoginRequest otherrequest = new LoginRequest("wrongusername", correctUser.getPassword());
        Response response = service.login(request);
        assertFalse(response.isSuccess());
//        assertThrows(service.login(otherrequest));
        assertFalse(service.login(otherrequest).isSuccess());
    }
}