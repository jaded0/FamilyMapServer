package RequestResult;

/**
 * Logs in the user and returns an auth token.
 */
public class LoginRequest extends Request{
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *Non-empty string
     */
    String userName;
    /**
     *Non-empty string
     */
    String password;
}
