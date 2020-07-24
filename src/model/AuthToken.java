package model;

import java.security.SecureRandom;
import java.util.Base64;

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
    private String token;
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthToken() {
        this.token = generateNewToken();
    }
    public AuthToken(String token){this.token=token;}
}
