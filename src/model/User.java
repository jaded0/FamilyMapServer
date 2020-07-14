package model;

/**
 * The server should store information about each user account in its database.
 */
public class User {
    /**
     * Unique user name (non-empty string)
     */
    String Username;
    /**
     * User’s password (non-empty string)
     */
    String password;
    /**
     * User’s email address (non-empty string)
     */
    String emailAddress;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * User's first name (non-empty string)
     */
    String firstName;
    /**
     * User's last name (non-empty string)
     */
    String lastName;
    /**
     * User's gender (string: "f" or "m")
     */
    String gender;
    /**
     * Unique Person ID assigned to this user’s generated Person object
     * - see FamilyHistory Information section for details (non-empty string)
     */
    String personID;

}
