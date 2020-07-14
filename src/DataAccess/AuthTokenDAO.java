package DataAccess;

import model.AuthToken;
import model.User;

/**
 * All operations related to the auth token.
 */
public class AuthTokenDAO {
    /**
     * get a valid auth token
     * @param user
     * @return
     */
    public AuthToken getAuthToken(User user){
        return new AuthToken();
    }

    /**
     * verifies your token
     * @param auth
     * @param user
     * @return
     */
    public boolean verify(AuthToken auth, User user){
        return true;
    }
}
