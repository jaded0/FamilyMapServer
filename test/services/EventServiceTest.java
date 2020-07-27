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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EventServiceTest {

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
    void singleEvent() throws DataAccessException {
        // populate database with things
        UserDAO dao = new UserDAO(conn);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);
        User user = new User("username", "password", "email@mail.com", "firstname", "lastname", "m", "eventid");
        dao.insert(user);
        Person person = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "eventID");
        personDAO.insert(person);
        AuthToken token = authTokenDAO.newAuthToken(user);
        Event event = new Event("username", "eventid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "eventid");
        eventDAO.insert(event);
        db.closeConnection(true);

        // make service and yank event
        EventService ser = new EventService();
        EventSuccessResponse response = (EventSuccessResponse) ser.singleEvent(event.getEventID());
        assertEquals(response.getAssociatedUsername(),event.getAssociatedUsername());
        assertEquals(response.getEventID(), event.getEventID());
        assertEquals(response.getPersonID(), event.getPersonID());
        assertEquals(response.getLatitude(), event.getLatitude());
        assertEquals(response.getLongitude(), event.getLongitude());
        assertEquals(response.getCountry(), event.getCountry());
        assertEquals(response.getCity(), event.getCity());
        assertEquals(response.getEventType(), event.getEventType());
        assertEquals(response.getYear(), event.getYear());
    }

    @Test
    void singleEventFakeID() throws DataAccessException {
        // populate database with things
        UserDAO dao = new UserDAO(conn);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);
        User user = new User("username", "password", "email@mail.com", "firstname", "lastname", "m", "eventid");
        dao.insert(user);
        Person person = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "eventID");
        personDAO.insert(person);
        AuthToken token = authTokenDAO.newAuthToken(user);
        Event event = new Event("username", "eventid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "eventid");
        eventDAO.insert(event);
        db.closeConnection(true);

        // make service and DON't yank event
        EventService ser = new EventService();
        ErrorResponse response = (ErrorResponse) ser.singleEvent("worthlessid");
        assertFalse(response.isSuccess());
    }

    @Test
    void allEvents() throws DataAccessException {
        // we won't be needing that connection after all, and it interferes with our register
        db.closeConnection(true);
        // register a user to simplify things
        RegisterService register = new RegisterService();
        Response resp = register.register(new RegisterRequest("jaden", "password", "email@email.email", "jaden", "lorenc", "m"));
        RegisterSuccessResponse response = (RegisterSuccessResponse) resp;
        // get the event service
        EventService service = new EventService();
        // get the response containing all of the automatically generated events using the authtoken
        EventsSuccessResponse allEventsResult = (EventsSuccessResponse) service.allEvents(response.getAuthToken());
        // count up the ones received and make sure that they are the amount that is automatically generated in the table
        assertEquals(93, allEventsResult.getData().size());
        //db.closeConnection(false);
    }

    @Test
    void allEventsNoEvents() throws DataAccessException {
        // we won't be needing that connection after all
        db.closeConnection(true);
        // register a user to simplify things
        RegisterService register = new RegisterService();
        Response resp = register.register(new RegisterRequest("jaden", "password", "email@email.email", "jaden", "lorenc", "m"));
        RegisterSuccessResponse response = (RegisterSuccessResponse) resp;

        // undo the fill, take those people out.
        db = new Database();
        conn = db.getConnection();
        EventDAO eventDAO = new EventDAO(conn);
        eventDAO.clear();
        db.closeConnection(true);

        // get the event service
        EventService service = new EventService();
        // get the response containing all of the automatically generated events using the authtoken
        EventsSuccessResponse allEventsResult = (EventsSuccessResponse) service.allEvents(response.getAuthToken());
        // count up the ones received and make sure that they are the amount that is automatically generated in the table
        assertEquals(0, allEventsResult.getData().size());
        //db.closeConnection(false);
    }
}