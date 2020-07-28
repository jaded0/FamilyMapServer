package RequestResult;

/**
 *Models a single Event object with the specified ID.
 */
public class EventSuccessResponse extends Response{
    /**
     *Username of user account this event belongs to
     *(non-empty string)
     */
    private String associatedUsername;
    /**
     * Event’s unique ID (non-empty string)
     */
    private String eventID;
    /**
     * ID of the person this event belongs to (non-empty string)
     */
    private String personID;
    /**
     * Latitude of the event’s location (number)
     */
    private double latitude;
    /**
     * Longitude of the event’s location (number)
     */
    private double longitude;
    /**
     * Name of country where event occurred (non-empty string)
     */
    private String country;
    /**
     * Name of city where event occurred (non-empty string)
     */
    private String city;
    /**
     * Type of event ("birth", "baptism", etc.) (non-empty string)
     */
    private String eventType;
    /**
     * Year the event occurred (integer)
     */
    private int year;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    public EventSuccessResponse(String associatedUsername, String eventID, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        success = true;
    }
    public EventSuccessResponse(){
        success = true;
    }
}
