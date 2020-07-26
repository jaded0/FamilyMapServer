package services;

import DataAccess.*;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {

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
        db.closeConnection(true);
    }
    @Test
    void fillPeople() throws DataAccessException {
        // register a new user

        // set up userDAO
        // make user with random id
        PersonDAO personDAO = new PersonDAO(conn);
        String id = personDAO.generateID();
        User ourUser = new User("myusername",
                "mypass",
                "email@mail.com",
                "jaden",
                "lorenc",
                "m",
                id);
        //pass the connection to the DAO
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(ourUser);
        db.closeConnection(true);

        // now fill data for them

        FillService service = new FillService();
        service.fill(ourUser.getUserName(), 4);

        db = new Database();
        conn = db.getConnection();
        personDAO = new PersonDAO(conn);
        // this next line is something the IDE wanted me to do to get it to compile. so i did it
        PersonDAO finalPersonDAO = personDAO;
        assertDoesNotThrow(()-> fillTestHelper(ourUser.getPersonID(), finalPersonDAO, 4));

    }

    // yeah I'm really proud of this one.
    void fillTestHelper(String id, PersonDAO dao, int generation) throws DataAccessException {
        Person person =  dao.retrieve(id);
        if (generation>0){
            fillTestHelper(person.getFatherID(), dao, generation-1);
            fillTestHelper(person.getMotherID(), dao, generation-1);
        }
    }

    @Test
    void fillEvents() throws DataAccessException {
        // register a new user

        // set up userDAO
        // make user with random id
        PersonDAO personDAO = new PersonDAO(conn);
        String id = personDAO.generateID();
        User ourUser = new User("myusername",
                "mypass",
                "email@mail.com",
                "jaden",
                "lorenc",
                "m",
                id);
        //pass the connection to the DAO
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(ourUser);
        db.closeConnection(true);

        // now fill data for them

        FillService service = new FillService();
        service.fill(ourUser.getUserName(), 4);

        db = new Database();
        conn = db.getConnection();
        EventDAO eventDAO = new EventDAO(conn);
        assertDoesNotThrow(()-> eventDAO.getEventsForID(ourUser.getPersonID()));
        // test and make sure that three events were made for our person user
        assertEquals(3, eventDAO.getEventsForID(ourUser.getPersonID()).size());
    }
}