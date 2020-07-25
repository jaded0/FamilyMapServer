package services;

import DataAccess.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private Database db;
    private Connection conn;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        conn = db.getConnection();
        //db.clearTables();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }
    @Test
    void clear() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        // populate database with things
        UserDAO dao = new UserDAO(conn);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);
        User user = new User("username", "password", "email@mail.com", "firstname", "lastname", "m", "personid");
        dao.insert(user);
        Person person = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "personID");
        personDAO.insert(person);
        AuthToken token = authTokenDAO.newAuthToken(user);
        Event event = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "eventid");
        eventDAO.insert(event);
        db.closeConnection(true);

        // do the erase
        clearService.clear();

        // check and see that it's done

        // reconnect to the database
        db = new Database();
        conn = db.getConnection();
        dao = new UserDAO(conn);
        authTokenDAO = new AuthTokenDAO(conn);
        personDAO = new PersonDAO(conn);
        eventDAO = new EventDAO(conn);

        // make sure that it's all really erased
        UserDAO finalDao = dao;
        assertThrows(DataAccessException.class, ()-> finalDao.retrieve(user.getUsername()));
        assertFalse(authTokenDAO.verify(token));
        PersonDAO finalPersonDAO = personDAO;
        assertThrows(DataAccessException.class, ()-> finalPersonDAO.retrieve(person.getPersonID()));
        EventDAO finalEventDAO = eventDAO;
        assertThrows(DataAccessException.class, ()-> finalEventDAO.retrieve(event.getEventID()));
    }

    @Test
    void clearWhenCleanAlready() throws DataAccessException {
        // do the erase, but this time starting clean already
        ClearService clearService = new ClearService();
        clearService.clear();

        // check and see that it's done

        // reconnect to the database
        UserDAO dao = new UserDAO(conn);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);

        // make sure that it's all really erased
        User user = new User("username", "password", "email@mail.com", "firstname", "lastname", "m", "personid");
        Person person = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "personID");
        AuthToken token = new AuthToken();
        Event event = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "eventid");
        UserDAO finalDao = dao;

        assertThrows(DataAccessException.class, ()-> finalDao.retrieve(user.getUsername()));
        assertFalse(authTokenDAO.verify(token));
        PersonDAO finalPersonDAO = personDAO;
        assertThrows(DataAccessException.class, ()-> finalPersonDAO.retrieve(person.getPersonID()));
        EventDAO finalEventDAO = eventDAO;
        assertThrows(DataAccessException.class, ()-> finalEventDAO.retrieve(event.getEventID()));
    }
}