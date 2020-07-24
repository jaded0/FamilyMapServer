package RequestResult;

/**
 * Creates a new user account, generates 4 generations of ancestor data for the new
 * user, logs the user in, and returns an auth token.
 */
public class RegisterRequest extends Request{
    /**
     *Non-empty string
     */
    String userName;
    /**
     *Non-empty string
     */
    String password;
    /**
     * Non-empty string
     */
    String email;
    /**
     * Non-empty string
     */
    String firstName;
    /**
     * Non-empty string
     */
    String lastName;
    /**
     * Non-empty string
     */
    String gender;

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
