package DataAccess;

import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {
    private Database db;
    private Person ourPerson;
    private PersonDAO personDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        // make some random person
        ourPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 1);
        //Here, we'll open the connection in preparation for the test cases to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //pass the connection to the DAO so we can get started
        personDAO = new PersonDAO(conn);
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
        personDAO.insert(ourPerson);
        assertEquals(ourPerson,personDAO.retrieve(ourPerson.getPersonID()));
    }

    @Test
    void insertFails() throws DataAccessException, SQLException {
        Person otherPerson = new Person("othername", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 1);
        personDAO.insert(ourPerson);
        personDAO.insert(otherPerson);
        assertNotEquals(otherPerson,personDAO.retrieve(ourPerson.getPersonID()));
    }

    @Test
    void retrieve() throws DataAccessException, SQLException {
        personDAO.insert(ourPerson);
        Person otherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 1);
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("usernjjbiname", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 1);
        personDAO.insert(anotherPerson);
        Person yetanotherPerson = new Person("usehuibklrname", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 1);
        personDAO.insert(yetanotherPerson);
        assertEquals(ourPerson,personDAO.retrieve(ourPerson.getPersonID()));
    }

    @Test
    void retrieveFails() throws DataAccessException, SQLException {
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(ourPerson.getPersonID()));
    }

    @Test
    void clear() throws DataAccessException {
        personDAO.insert(ourPerson);
        Person otherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 2);
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 3);
        personDAO.insert(anotherPerson);
        Person yetanotherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", 4);
        personDAO.insert(yetanotherPerson);
        personDAO.clear();
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(ourPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(otherPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(anotherPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(yetanotherPerson.getPersonID()));
    }
}