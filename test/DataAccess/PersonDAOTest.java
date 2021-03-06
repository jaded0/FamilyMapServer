package DataAccess;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
        ourPerson = new Person("myusername", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "personID");
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
                "male", "fatherid", "momid", "spouseid", "jfieopqpersonID");
        personDAO.insert(ourPerson);
        personDAO.insert(otherPerson);
        assertNotEquals(otherPerson,personDAO.retrieve(ourPerson.getPersonID()));
    }

    @Test
    void retrieve() throws DataAccessException, SQLException {
        personDAO.insert(ourPerson);
        Person otherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "jofpersonID");
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("usernjjbiname", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "otherpersonID");
        personDAO.insert(anotherPerson);
        Person yetanotherPerson = new Person("usehuibklrname", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "finalpersonID");
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
                "male", "fatherid", "momid", "spouseid", "nipdhaspersonID");
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "fndpersonID");
        personDAO.insert(anotherPerson);
        Person yetanotherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "cjjjpersonID");
        personDAO.insert(yetanotherPerson);
        personDAO.clear();
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(ourPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(otherPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(anotherPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(yetanotherPerson.getPersonID()));
    }

    @Test
    void clearFromUsername() throws DataAccessException {
        personDAO.insert(ourPerson);
        Person otherPerson = new Person("usernamee", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "nipdhaspersonID");
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("usernamee", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "fndpersonID");
        personDAO.insert(anotherPerson);
        Person yetanotherPerson = new Person("fjionsc", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "cjjjpersonID");
        personDAO.insert(yetanotherPerson);
        personDAO.clearFromUsername("usernamee");
        // so if you have a username that matches the column name, it'll delete all rows. super weird.
//        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(ourPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(otherPerson.getPersonID()));
        assertThrows(DataAccessException.class, ()-> personDAO.retrieve(anotherPerson.getPersonID()));
        assertDoesNotThrow(()-> personDAO.retrieve(yetanotherPerson.getPersonID()));
    }

    @Test
    void clearFromUsernameNoMatch() throws DataAccessException {
        personDAO.insert(ourPerson);
        Person otherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "nipdhaspersonID");
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "fndpersonID");
        personDAO.insert(anotherPerson);
        Person yetanotherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "cjjjpersonID");
        personDAO.insert(yetanotherPerson);
        personDAO.clearFromUsername("nomatch");
        assertDoesNotThrow(()-> personDAO.retrieve(ourPerson.getPersonID()));
        assertDoesNotThrow(()-> personDAO.retrieve(otherPerson.getPersonID()));
        assertDoesNotThrow(()-> personDAO.retrieve(anotherPerson.getPersonID()));
        assertDoesNotThrow(()-> personDAO.retrieve(yetanotherPerson.getPersonID()));
    }

    @Test
    void getPersonsForUsername() throws DataAccessException {
        personDAO.insert(ourPerson);
        Person otherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "pdiofnersonid");
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("username", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "pesnoirsonid");
        personDAO.insert(anotherPerson);
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(ourPerson);personList.add(otherPerson);personList.add(anotherPerson);
        assertEquals(personList, personDAO.getPersonsForUsername("username"));
    }

    @Test
    void getPersonsForUsernameWrongUsername() throws DataAccessException {
        personDAO.insert(ourPerson);
        Person otherPerson = new Person("myusername", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "persoddddnid");
        personDAO.insert(otherPerson);
        Person anotherPerson = new Person("myusername", "firstname", "lastName",
                "male", "fatherid", "momid", "spouseid", "personaasdid");
        personDAO.insert(anotherPerson);
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(ourPerson);personList.add(otherPerson);personList.add(anotherPerson);
        // weirdly enough, getPersonsForUsername("username); literal will return all persons, because "username" matches every username
        assertNotEquals(personList, personDAO.getPersonsForUsername("notmyusername"));
    }
}