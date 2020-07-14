package RequestResult;

/**
 * General response model.
 */
public abstract class Response {
    /**
     * Every response will have this boolean value.
     */
    boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
