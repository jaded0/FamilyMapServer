package RequestResult;

/**
 * Return auth token after login
 */
public class LoginSuccessResponse extends Response{
    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }

    public LoginSuccessResponse(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.success = true;
    }

    /**
     * Non-empty auth token string
     */
    String authToken;
    /**
     * User name passed in with request
     */
    String userName;
    /**
     * Non-empty string containing the Person ID of the
     * user’s generated Person object
     */
    String personID;
}
