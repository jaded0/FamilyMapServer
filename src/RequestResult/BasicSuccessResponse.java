package RequestResult;

/**
 * The most basic success response has only a message and boolean success value.
 * Works with load and fill.
 */
public class BasicSuccessResponse extends Response {
    /**
     * Description of the success.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
