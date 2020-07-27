package RequestResult;

import model.Event;
import model.Person;

import java.util.ArrayList;

/**
 * Models a response for ALL events for ALL family data of the current user.
 */
public class EventsSuccessResponse extends Response{
    /**
     * Array of event objects.
     */
    private ArrayList<Event> data;

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public void setMembers(ArrayList<Event> data) { this.data = data;}
}
