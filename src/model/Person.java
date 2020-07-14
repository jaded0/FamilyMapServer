package model;

/**
 * All details associated with a person;
 */
public class Person {
    /**
     * Unique identifier for this person (non-empty string)
     */
    String personID;
    /**
     * User (Username) to which this person belongs
     */
    String username;
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
}
