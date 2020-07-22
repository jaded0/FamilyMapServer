package DataAccess;

import java.sql.*;

public class Database {
    private Connection conn;

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // make our database, but only makes one table?
//            String sql ="CREATE TABLE if not exists users (\n" +
//                    "\t\"username\"\tTEXT NOT NULL,\n" +
//                    "\t\"password\"\tTEXT NOT NULL,\n" +
//                    "\t\"email\" TEXT NOT NULL,\n" +
//                    "\t\"FirstName\"\tTEXT NOT NULL,\n" +
//                    "\t\"LastName\"\tTEXT NOT NULL,\n" +
//                    "\t\"Gender\"\tTEXT NOT NULL,\n" +
//                    "\t\"personID\"\tTEXT NOT NULL UNIQUE\n" +
//                    "--\t\"person ID\"\tINTEGER NOT NULL UNIQUE,\n" +
//                    "--\tPRIMARY KEY(\"person ID\" AUTOINCREMENT)\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE if not exists \"authtokens\" (\n" +
//                    "\t\"token\"\tTEXT NOT NULL UNIQUE,\n" +
//                    "\t\"timestamp\" text NOT NULL,\n" +
//                    "\t\"person ID\"\tINTEGER NOT NULL UNIQUE\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE if not exists \"persons\" (\n" +
//                    "\t\"username\"\tTEXT NOT NULL,\n" +
//                    "\t\"First Name\"\tTEXT NOT NULL,\n" +
//                    "\t\"Last Name\"\tTEXT NOT NULL,\n" +
//                    "\t\"Gender\"\tTEXT NOT NULL,\n" +
//                    "\t\"father id\" TEXT,\n" +
//                    "\t\"mother id\" TEXT,\n" +
//                    "\t\"spouse id\" TEXT,\n" +
//                    "\t\"person ID\"\tINTEGER NOT NULL UNIQUE,\n" +
//                    "\tPRIMARY KEY(\"person ID\" AUTOINCREMENT)\n" +
//                    ");\n" +
//                    "\n" +
//                    "CREATE TABLE if not exists \"events\" (\n" +
//                    "\t\"username\"\tTEXT NOT NULL,\n" +
//                    "\t\"person id\" TEXT NOT NULL,\n" +
//                    "\t\"latitude\" INTEGER NOT NULL,\n" +
//                    "\t\"longitude\" INTEGER NOT NULL,\n" +
//                    "\t\"country\" TEXT NOT NULL,\n" +
//                    "\t\"city\" TEXT NOT NULL,\n" +
//                    "\t\"EventType\" TEXT NOT NULL check (\"EventType\" in ('birth', 'baptism', 'christening', 'marriage', 'death')),\n" +
//                    "\t\"event id\"\tINTEGER NOT NULL UNIQUE,\n" +
//                    "\tPRIMARY KEY(\"event id\" AUTOINCREMENT)\n" +
//                    "\t--constraint ck_type check (\"EventType\" in ('birth', 'baptism', 'christening', 'marriage', 'death')),\n" +
//                    ");";
//            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                stmt.execute();
//            } catch (SQLException e) {
//                throw new DataAccessException("Error encountered while initially inserting into the database");
//            }

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void clearTables() throws DataAccessException
    {

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}

