package RequestResult;

import java.util.ArrayList;

/**
 * Model for ALL family members of the current user.
 */
public class PersonsSuccessResponse extends Response{
    /**
     * Stores all family members of the current user.
     */
    private ArrayList<String> members;

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
}
