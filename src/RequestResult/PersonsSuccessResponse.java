package RequestResult;

import model.Person;

import java.util.ArrayList;

/**
 * Model for ALL family members of the current user.
 */
public class PersonsSuccessResponse extends Response{
    /**
     * Stores all family members of the current user.
     */
    private ArrayList<Person> members;

    public ArrayList<Person> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Person> members) {
        this.members = members;
    }
}
