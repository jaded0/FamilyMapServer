package model;

import java.util.Objects;

/**
 * The server should store information about each user account in its database.
 */
public class User {
    /**
     * Unique user name (non-empty string)
     */
    String username;
    /**
     * User’s password (non-empty string)
     */
    String password;
    /**
     * User’s email address (non-empty string)
     */
    String emailAddress;
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

    public User(String username, String password, String emailAddress, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername()) &&
                getPassword().equals(user.getPassword()) &&
                getEmailAddress().equals(user.getEmailAddress()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getGender().equals(user.getGender()) &&
                getPersonID().equals(user.getPersonID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmailAddress(), getFirstName(), getLastName(), getGender(), getPersonID());
    }
}
