package RequestResult;

/**
 * Creates a new user account, generates 4 generations of ancestor data for the new
 * user, logs the user in, and returns an auth token.
 */
public class RegisterRequest {
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
}
