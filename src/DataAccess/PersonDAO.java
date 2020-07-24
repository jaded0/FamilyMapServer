package DataAccess;

import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * all operations related to a person object
 */
public class PersonDAO {
    private final Connection conn;

    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * insert a new person
     * @param person The person model object to fill the database with
     */
    public void insert(Person person) throws DataAccessException{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO persons (username, FirstName, LastName, " +
                "Gender, \"father id\", \"mother id\", \"spouse id\", personID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getUsername());
            stmt.setString(2, person.getFirstName());
            stmt.setString(3, person.getLastName());
            stmt.setString(4, person.getGender());
            stmt.setString(5, person.getMotherID());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getSpouseID());
            stmt.setString(8, person.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
    }

    /**
     * get the Person info
     * @param personID
     */
    public Person retrieve(String personID) throws DataAccessException {
        String sql = "SELECT username, FirstName, LastName, Gender, \"mother id\", \"father id\", \"spouse id\", personID " +
                "FROM persons " +
                "WHERE personID=\'" + personID + "\'";

        Person result;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            result = new Person(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while querying in the database");
        }

        return result;
    }

    /**
     * CLEAR *zap* *boom*
     * they're all dead, there's no trace of any person anymore.
     */
    public void clear() throws DataAccessException {
        try(PreparedStatement statement = conn.prepareStatement("DELETE FROM persons;")){
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Error when trying to clear");
        }
    }

    /**
     * gets all the people
     * @return
     */
    public ArrayList<Person> getPersons(){
        return new ArrayList<Person>();
    }

    /**
     * fill generational data for this person.
     * @param person
     */
    public void fill(Person person){

    }
}
