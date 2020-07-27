package DataAccess;

import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.FillService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {
    private Database db;
    private Event ourEvent;
    private EventDAO eventDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        // make some random event
        ourEvent = new Event("myusername", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "eventid");
        //Here, we'll open the connection in preparation for the test cases to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //pass the connection to the DAO so we can get started
        eventDAO = new EventDAO(conn);
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    void insert() throws DataAccessException {
        eventDAO.insert(ourEvent);
        assertEquals(ourEvent,eventDAO.retrieve(ourEvent.getEventID()));
    }

    @Test
    void insertFails() throws DataAccessException, SQLException {
        Event otherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "othereventid");
        eventDAO.insert(ourEvent);
        eventDAO.insert(otherEvent);
        assertNotEquals(otherEvent,eventDAO.retrieve(ourEvent.getEventID()));
    }

    @Test
    void retrieve() throws DataAccessException, SQLException {
        eventDAO.insert(ourEvent);
        Event otherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "jcioeventid");
        eventDAO.insert(otherEvent);
        Event anotherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "dffeventid");
        eventDAO.insert(anotherEvent);
        Event yetanotherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "asdeventid");
        eventDAO.insert(yetanotherEvent);
        assertEquals(ourEvent,eventDAO.retrieve(ourEvent.getEventID()));
    }

    @Test
    void retrieveFails() throws DataAccessException, SQLException {
        assertThrows(DataAccessException.class, ()-> eventDAO.retrieve(ourEvent.getEventID()));
    }

    @Test
    void clear() throws DataAccessException {
        eventDAO.insert(ourEvent);
        Event otherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "djfieventid");
        eventDAO.insert(otherEvent);
        Event anotherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "dkmkeventid");
        eventDAO.insert(anotherEvent);
        Event yetanotherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "fmieventid");
        eventDAO.insert(yetanotherEvent);
        eventDAO.clear();
        assertThrows(DataAccessException.class, ()-> eventDAO.retrieve(ourEvent.getEventID()));
        assertThrows(DataAccessException.class, ()-> eventDAO.retrieve(otherEvent.getEventID()));
        assertThrows(DataAccessException.class, ()-> eventDAO.retrieve(anotherEvent.getEventID()));
        assertThrows(DataAccessException.class, ()-> eventDAO.retrieve(yetanotherEvent.getEventID()));
    }

    @Test
    void getEventsForID() throws DataAccessException {
        eventDAO.insert(ourEvent);
        Event otherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "djfieventid");
        eventDAO.insert(otherEvent);
        Event anotherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "dkmkeventid");
        eventDAO.insert(anotherEvent);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(ourEvent);eventList.add(otherEvent);eventList.add(anotherEvent);
        assertEquals(eventList, eventDAO.getEventsForID("personid"));
    }

    @Test
    void getEventsForIDWrongID() throws DataAccessException {
        eventDAO.insert(ourEvent);
        Event otherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "djfieventid");
        eventDAO.insert(otherEvent);
        Event anotherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "dkmkeventid");
        eventDAO.insert(anotherEvent);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(ourEvent);eventList.add(otherEvent);eventList.add(anotherEvent);
        assertNotEquals(eventList, eventDAO.getEventsForID("noidthatworks"));
    }

    @Test
    void getEventsForUsername() throws DataAccessException {
        eventDAO.insert(ourEvent);
        Event otherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "djfieventid");
        eventDAO.insert(otherEvent);
        Event anotherEvent = new Event("username", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "dkmkeventid");
        eventDAO.insert(anotherEvent);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(ourEvent);eventList.add(otherEvent);eventList.add(anotherEvent);
        assertEquals(eventList, eventDAO.getEventsForUsername("username"));
    }

    @Test
    void getEventsForUsernameWrongUsername() throws DataAccessException {
        eventDAO.insert(ourEvent);
        Event otherEvent = new Event("myusername", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "djfieventid");
        eventDAO.insert(otherEvent);
        Event anotherEvent = new Event("myusername", "personid", 12345.0, 12345.0, "USA", "zion", "birth", 2020, "dkmkeventid");
        eventDAO.insert(anotherEvent);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(ourEvent);eventList.add(otherEvent);eventList.add(anotherEvent);
        // weirdly enough, getEventsForUsername("username); literal will return all events, because "username" matches every username
        assertNotEquals(eventList, eventDAO.getEventsForUsername("nottheusername"));
    }
}
