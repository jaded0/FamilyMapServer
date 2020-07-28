package services;

import DataAccess.*;
import RequestResult.BasicSuccessResponse;
import RequestResult.ErrorResponse;
import RequestResult.Response;
import com.google.gson.Gson;
import model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.UUID;

/**
 * Populates the server's database with generated data for the specified user name.
 */
public class FillService extends Service{
    private int personsAdded;
    private int eventsAdded;
    /**
     * fll ya
     * @return
     */
    public Response fill(String username, int generations){
        personsAdded = 0;
        eventsAdded = 0;
        PersonDAO dao = new PersonDAO(conn);
        UserDAO userDAO = new UserDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);
        try {
            // get user
            User user = userDAO.retrieve(username);

            // start from a clean slate
            dao.clearFromUsername(username);

            // recursively fill with generated data
            fillHelper(user.getFirstName(), user.getLastName(), user.getGender(),generations,dao,eventDAO,username,user.getPersonID(),null, 2025, null); // sad

        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
            return new ErrorResponse("Error while filling");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
        return new BasicSuccessResponse("Successfully added " + personsAdded + " persons and " + eventsAdded + " events to the database.");
    }
    private void fillHelper(String firstName, String lastName, String gender, int generation, PersonDAO dao, EventDAO eventDAO, String username, String id,
                            String spouseID, int childsBirthYear, Event marriage) throws DataAccessException, FileNotFoundException {

        // fill life events
        int birthYear = childsBirthYear-20;

        fillEvents(username,id,childsBirthYear,eventDAO, marriage);
        Person person;

        // randomly generate personal details from a file
        // get files
        FileReader reader = new FileReader((gender.equals("m")?"json/mnames.json":"json/fnames.json"));
        // convert them to usable objects from json
        Gson gson = new Gson();
        NamesData namesData = gson.fromJson(reader, NamesData.class);
        // assign them to variables to be used in the database
        if (firstName==null) firstName = namesData.getRandomName();
        //repeat
        reader = new FileReader("json/snames.json");
        namesData = gson.fromJson(reader, NamesData.class);
        if (lastName == null) lastName = namesData.getRandomName();
//        reader.close();

        // check whether to generate parents or not, then generate person
        if (generation>0){
            // make parents with random id
            String dadID = dao.generateID();
            String momID = dao.generateID();
            Event parentsMarriage = generateRandomEvent(username, dadID, eventDAO, "marriage", childsBirthYear-3);
            // fill father, and ancestors (generate first and last name)
            fillHelper(null, null,"m", generation-1, dao, eventDAO, username, dadID, momID, birthYear, parentsMarriage);
            // make the mom's marriage as well
            parentsMarriage.setPersonID(momID);parentsMarriage.setEventID(dao.generateID());
            // fill father, and ancestors (generate first and last name)
            fillHelper(null, null, "f", generation-1, dao, eventDAO, username, momID, dadID, birthYear, parentsMarriage);
            // create the person
            person = new Person(username,
                    firstName,
                    lastName,
                    gender,
                    dadID,
                    momID,
                    spouseID,
                    id);
        } else {
            person = new Person(username,
                    firstName,
                    lastName,
                    gender,
                    null,
                    null,
                    spouseID,
                    id);
        }
        dao.insert(person);
        personsAdded++;
    }

    private void fillEvents(String username, String personID, int childsBirthYear, EventDAO dao, Event marriage) throws FileNotFoundException, DataAccessException {
        dao.insert(generateRandomEvent(username, personID, dao, "birth",childsBirthYear-20));
        eventsAdded++;

        // fill birth
        if (marriage != null) dao.insert(marriage);
        else dao.insert(generateRandomEvent(username, personID, dao, "marriage", childsBirthYear-3));
        eventsAdded++;

        // fill birth
        dao.insert(generateRandomEvent(username, personID, dao, "death", childsBirthYear+60));
        eventsAdded++;
    }

    private Event generateRandomEvent(String username, String personID, EventDAO dao, String type, int year) throws FileNotFoundException {
        FileReader reader = new FileReader("json/locations.json");
        // convert them to usable objects from json
        Gson gson = new Gson();
        LocationData locationData = gson.fromJson(reader, LocationData.class);
        Location location = locationData.getRandomLocation();
        return new Event(username,
                personID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                type,
                year,
                dao.generateID());
    }
}

