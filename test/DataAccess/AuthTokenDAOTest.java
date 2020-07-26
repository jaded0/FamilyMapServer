package DataAccess;

import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDAOTest {

    private Database db;
    private User ourUser;
    private AuthTokenDAO tokenDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
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
        tokenDAO = new AuthTokenDAO(conn);
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    void newAuthToken() throws DataAccessException {
        AuthToken token = tokenDAO.newAuthToken(ourUser);
        assertTrue(tokenDAO.verify(token));
    }

    @Test
    void newAuthTokenFails() throws DataAccessException {
        AuthToken token = tokenDAO.newAuthToken(ourUser);
        AuthToken falseToken = new AuthToken();
        assertFalse(tokenDAO.verify(falseToken));
    }

    @Test
    void verify() throws DataAccessException {
        AuthToken token = tokenDAO.newAuthToken(ourUser);
        assertTrue(tokenDAO.verify(token));
        // test whether it accepts a token for the same user
        AuthToken sectoken = tokenDAO.newAuthToken(ourUser);
        assertTrue(tokenDAO.verify(sectoken));
        User otherUser = new User("otherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "some alternate sort of id");
        AuthToken othertoken = tokenDAO.newAuthToken(ourUser);
        assertTrue(tokenDAO.verify(othertoken));
        User anotherUser = new User("moretido", "password", "address@email.com",
                "firstname", "lastname", "female", "more alternate sort of id");
        AuthToken yetantoken = tokenDAO.newAuthToken(ourUser);
        assertTrue(tokenDAO.verify(yetantoken));
        User yetanotherUser = new User("yetanotherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "even more alternate sort of id");
        AuthToken jfieotoken = tokenDAO.newAuthToken(ourUser);
        assertTrue(tokenDAO.verify(jfieotoken));
    }

    @Test
    void verifyFails() throws DataAccessException {
        AuthToken notoken = tokenDAO.newAuthToken(ourUser);
        AuthToken token = new AuthToken();
        assertFalse(tokenDAO.verify(token));
        assertTrue(tokenDAO.verify(notoken));
    }

    @Test
    void clear() throws DataAccessException {
        AuthToken token = tokenDAO.newAuthToken(ourUser);
        // test whether it accepts a token for the same user
        AuthToken sectoken = tokenDAO.newAuthToken(ourUser);
        User otherUser = new User("otherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "some alternate sort of id");
        AuthToken othertoken = tokenDAO.newAuthToken(ourUser);
        User anotherUser = new User("moretido", "password", "address@email.com",
                "firstname", "lastname", "female", "more alternate sort of id");
        AuthToken yetantoken = tokenDAO.newAuthToken(ourUser);
        User yetanotherUser = new User("yetanotherusername", "password", "address@email.com",
                "firstname", "lastname", "female", "even more alternate sort of id");
        AuthToken jfieotoken = tokenDAO.newAuthToken(ourUser);
        tokenDAO.clear();
        assertFalse(tokenDAO.verify(sectoken));
        assertFalse(tokenDAO.verify(othertoken));
        assertFalse(tokenDAO.verify(yetantoken));
        assertFalse(tokenDAO.verify(jfieotoken));
    }

    @Test
    void getUsernameForAuthtoken() throws DataAccessException {
        AuthToken token = tokenDAO.newAuthToken(ourUser);
        assertEquals(ourUser.getUserName(), tokenDAO.getUsernameForAuthtoken(token.getToken()));
    }

    @Test
    void getUsernameForAuthtokenDoesntExist() {
        // this will throw an exception because that auth token cannot be found
        assertThrows(DataAccessException.class, ()-> tokenDAO.getUsernameForAuthtoken("nothinguseful"));
    }
}