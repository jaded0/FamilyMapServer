package RequestResult;

/**
 * Every error response is the same.
 */
public class ErrorResponse extends Response{
    /**
     * Description of the error.
     */
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
