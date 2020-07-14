package RequestResult;

/**
 * return credentials and auth token
 */
public class RegisterSuccessResponse extends Response{
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
    String personID;}
