package model;

/**
 * If login succeeds, your server should generate a unique "authorization token"
 * string for the user, and return it to the client.  Subsequent requests
 * sent from the client to your server should include the auth token so
 * your server can determine which user is making the request.
 */
public class AuthToken {
    /**
     * the token itself
     */
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
