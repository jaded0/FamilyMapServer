package model;

import java.util.Objects;

/**
 * All details associated with a person;
 */
public class Person {
    /**
     * User (Username) to which this person belongs
     */
    String associatedUsername;
    /**
     * Person's first name (non-empty string)
     */
    String firstName;
    /**
     * Person's last name (non-empty string)
     */
    String lastName;
    /**
     * Person's gender (string: "f" or "m")
     */
    String gender;
    /**
     * Person ID of person's father (possibly null)
     */
    String fatherID;
    /**
     * Person ID of person's mother (possibly null)
     */
    String motherID;
    /**
     * Person ID of person's spouse (possibly null)
     */
    String spouseID;
    /**
     * Unique identifier for this person (non-empty string)
     */
    String personID;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Person(String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, String personID) {
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getAssociatedUsername().equals(person.getAssociatedUsername()) &&
                getFirstName().equals(person.getFirstName()) &&
                getLastName().equals(person.getLastName()) &&
                getGender().equals(person.getGender()) &&
                Objects.equals(getFatherID(), person.getFatherID()) &&
                Objects.equals(getMotherID(), person.getMotherID()) &&
                Objects.equals(getSpouseID(), person.getSpouseID()) &&
                getPersonID().equals(person.getPersonID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssociatedUsername(), getFirstName(), getLastName(), getGender(), getFatherID(), getMotherID(), getSpouseID(), getPersonID());
    }
}
