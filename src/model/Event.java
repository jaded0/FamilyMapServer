package model;

import java.util.Objects;

/**
 * All details associated with an event
 */
public class Event {
    public Event(String username, String personID, double latitude, double longitude, String country, String city, String eventType, int year, String eventID) {
        this.eventID = eventID;
        this.username = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Unique identifier for this event (non-empty string)
     */
    String eventID;
    /**
     * User (Username) to which this person belongs
     */
    String username;
    /**
     * ID of person to which this event belongs
     */
    String personID;
    /**
     * Latitude of event’s location
     */
    double latitude;
    /**
     * Longitude of event’s location
     */
    double longitude;
    /**
     * Country in which event occurred
     */
    String country;
    /**
     * City in which event occurred
     */
    String city;
    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    String eventType;
    /**
     * Year in which event occurred
     */
    int year;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Double.compare(event.getLatitude(), getLatitude()) == 0 &&
                Double.compare(event.getLongitude(), getLongitude()) == 0 &&
                getYear() == event.getYear() &&
                getEventID().equals(event.getEventID()) &&
                getUsername().equals(event.getUsername()) &&
                getPersonID().equals(event.getPersonID()) &&
                getCountry().equals(event.getCountry()) &&
                getCity().equals(event.getCity()) &&
                getEventType().equals(event.getEventType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventID(), getUsername(), getPersonID(), getLatitude(), getLongitude(), getCountry(), getCity(), getEventType(), getYear());
    }
}
