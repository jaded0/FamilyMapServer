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
    private ArrayList<Person> data;

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public PersonsSuccessResponse() {
        success = true;
    }
}
