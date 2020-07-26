package RequestResult;

/**
 * Model for the single Person object with the specified ID.
 */
public class PersonSuccessResponse extends Response{
    public PersonSuccessResponse(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Name of user account this person belongs to.
     */
    private String associatedUsername;
    /**
     * Person’s unique ID
     */
    private String personID;
    /**
     * Person’s first name.
     */
    private String firstName;
    /**
     * Person’s last name.
     */
    private String lastName;
    /**
     * Person's gender ("m" or "f").
     */
    private String gender;
    /**
     * ID of person’s father ​[OPTIONAL, can be missing].
     */
    private String fatherID;
    /**
     * ID of person’s mother ​[OPTIONAL, can be missing].
     */
    private String motherID;
    /**
     * ID of person’s spouse ​[OPTIONAL, can be missing].
     */
    private String spouseID;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
