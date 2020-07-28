package services;

import DataAccess.*;
import RequestResult.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private Database db;
    private Connection conn;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        conn = db.getConnection();
        db.clearTables();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
//        db.closeConnection(false);
    }

    @Test
    void singlePerson() throws DataAccessException {
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

        // make service and yank person
        PersonService ser = new PersonService();
        PersonSuccessResponse response = (PersonSuccessResponse) ser.singlePerson(person.getPersonID(), token.getToken());
        assertEquals(response.getAssociatedUsername(),person.getAssociatedUsername());
        assertEquals(response.getFatherID(), person.getFatherID());
        assertEquals(response.getFirstName(), person.getFirstName());
        assertEquals(response.getGender(), person.getGender());
        assertEquals(response.getLastName(), person.getLastName());
        assertEquals(response.getMotherID(), person.getMotherID());
        assertEquals(response.getPersonID(), person.getPersonID());
        assertEquals(response.getSpouseID(), person.getSpouseID());
    }

    @Test
    void singlePersonFakeID() throws DataAccessException {
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

        // make service and DON't yank person
        PersonService ser = new PersonService();
        ErrorResponse response = (ErrorResponse) ser.singlePerson("worthlessid", token.getToken());
        assertFalse(response.isSuccess());
    }

    @Test
    void allPersons() throws DataAccessException {
        // we won't be needing that connection after all, and it interferes with our register
        db.closeConnection(true);
        // register a user to simplify things
        RegisterService register = new RegisterService();
        Response resp = register.register(new RegisterRequest("jaden", "password", "email@email.email", "jaden", "lorenc", "m"));
        RegisterSuccessResponse response = (RegisterSuccessResponse) resp;
        // get the person service
        PersonService service = new PersonService();
        // get the response containing all of the automatically generated persons using the authtoken
        PersonsSuccessResponse allPersonsResult = (PersonsSuccessResponse) service.allPersons(response.getAuthToken());
        // count up the ones received and make sure that they are the amount that is automatically generated in the table
        assertEquals(31, allPersonsResult.getData().size());
        //db.closeConnection(false);
    }

    @Test
    void allPersonsNoPersons() throws DataAccessException {
        // we won't be needing that connection after all
        db.closeConnection(true);
        // register a user to simplify things
        RegisterService register = new RegisterService();
        Response resp = register.register(new RegisterRequest("jaden", "password", "email@email.email", "jaden", "lorenc", "m"));
        RegisterSuccessResponse response = (RegisterSuccessResponse) resp;

        // undo the fill, take those people out.
        db = new Database();
        conn = db.getConnection();
        PersonDAO personDAO = new PersonDAO(conn);
        personDAO.clear();
        db.closeConnection(true);

        // get the person service
        PersonService service = new PersonService();
        // get the response containing all of the automatically generated persons using the authtoken
        PersonsSuccessResponse allPersonsResult = (PersonsSuccessResponse) service.allPersons(response.getAuthToken());
        // count up the ones received and make sure that they are the amount that is automatically generated in the table
        assertEquals(0, allPersonsResult.getData().size());
        //db.closeConnection(false);
    }
}