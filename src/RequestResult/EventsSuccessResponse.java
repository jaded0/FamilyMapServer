package RequestResult;

import java.util.ArrayList;

/**
 * Models a response for ALL events for ALL family members of the current user.
 */
public class EventsSuccessResponse extends Response{
    /**
     * Array of event objects.
     */
    private ArrayList data;

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
