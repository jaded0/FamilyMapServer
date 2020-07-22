package DataAccess;

import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private Database db;
    private User ourUser;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws DataAccessException{
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        // make some random user
        ourUser = new User("username", "password", "address@email.com",
                "firstname", "lastname", "female", "some sort of id");
        //Here, we'll open the connection in preparation for the test cases to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //pass the connection to the DAO so we can get started
        userDAO = new UserDAO(conn);
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
        userDAO.insert(ourUser);
        assertEquals(ourUser,userDAO.retrieve(ourUser.getUsername()));
    }

    @Test
    void insertFails() throws DataAccessException, SQLException {
        User otherUser = new User("otherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "some alternate sort of id");
        userDAO.insert(ourUser);
        userDAO.insert(otherUser);
        assertNotEquals(otherUser,userDAO.retrieve(ourUser.getUsername()));
        assertThrows(DataAccessException.class, ()-> userDAO.insert(ourUser));
    }

    @Test
    void retrieve() throws DataAccessException, SQLException {
        userDAO.insert(ourUser);
        User otherUser = new User("otherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "some alternate sort of id");
        userDAO.insert(otherUser);
        User anotherUser = new User("moretido", "password", "address@email.com",
                "firstname", "lastname", "female", "more alternate sort of id");
        userDAO.insert(anotherUser);
        User yetanotherUser = new User("yetanotherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "even more alternate sort of id");
        userDAO.insert(yetanotherUser);
        assertEquals(ourUser,userDAO.retrieve(ourUser.getUsername()));
    }

    @Test
    void retrieveFails() throws DataAccessException, SQLException {
        assertThrows(DataAccessException.class, ()-> userDAO.retrieve(ourUser.getUsername()));
    }

    @Test
    void clear() throws DataAccessException {
        userDAO.insert(ourUser);
        User otherUser = new User("otherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "some alternate sort of id");
        userDAO.insert(otherUser);
        User anotherUser = new User("moretido", "password", "address@email.com",
                "firstname", "lastname", "female", "more alternate sort of id");
        userDAO.insert(anotherUser);
        User yetanotherUser = new User("yetanotherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "even more alternate sort of id");
        userDAO.insert(yetanotherUser);
        userDAO.clear();
        assertThrows(DataAccessException.class, ()-> userDAO.retrieve(ourUser.getUsername()));
        assertThrows(DataAccessException.class, ()-> userDAO.retrieve(otherUser.getUsername()));
        assertThrows(DataAccessException.class, ()-> userDAO.retrieve(anotherUser.getUsername()));
        assertThrows(DataAccessException.class, ()-> userDAO.retrieve(yetanotherUser.getUsername()));
    }
}