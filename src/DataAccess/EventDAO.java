package DataAccess;

import model.Event;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * all operations related to an event
 */
public class EventDAO {
    private final Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * insert a new event
     * @param event The event model object to fill the database with
     */
    public void insert(Event event) throws DataAccessException{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO events (username, personID, latitude, longitude, country, " +
                                "city, EventType, year, \"event id\") VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getUsername());
            stmt.setString(2, event.getPersonID());
            stmt.setDouble(3, event.getLatitude());
            stmt.setDouble(4, event.getLongitude());
            stmt.setString(5, event.getCountry());
            stmt.setString(6, event.getCity());
            stmt.setString(7, event.getEventType());
            stmt.setInt(8, event.getYear());
            stmt.setString(9, event.getEventID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting event into the database");
        }
    }

    /**
     * get the Event info
     * @param eventID
     */
    public Event retrieve(String eventID) throws DataAccessException {
        String sql = "SELECT username, personID, latitude, longitude, country, city, EventType, year, \"event id\" " +
                "FROM events " +
                "WHERE \"event id\"=\'" + eventID + "\'";

        Event result;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            result = new Event(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9));
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while querying in the database");
        }

        return result;
    }

    /**
     * gets all the events
     * @return
     */
    public ArrayList<Event> getEvents(){
        return new ArrayList<Event>();
    }

    /**
     * fill event data for this user.
     * @param user
     */
    public void fill(User user){

    }

    /**
     * CLEAR *zap* *boom*
     * they're all dead, there's no trace of any user anymore.
     */
    public void clear() throws DataAccessException {
        try(PreparedStatement statement = conn.prepareStatement("DELETE FROM events;")){
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Error when trying to clear");
        }
    }
}
