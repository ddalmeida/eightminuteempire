package logic.cards;

import java.io.Serializable;
import logic.actions.BaseAction;

public class RegularCard implements Serializable {

    String resource;
    int resourceNumber;
    BaseAction action;
    static int idc = 0;
    int id;

    public RegularCard(String resource, int resourceNumber, BaseAction action) {
        this.resource = resource;
        this.resourceNumber = resourceNumber;
        this.action = action;
        id = idc++;
    }

    public String getResource() {
        return resource;
    }

    public int getResourceNumber() {
        return resourceNumber;
    }

    public BaseAction getAction() {
        return action;
    }

    public String toString() {
        return resource + " card with " + action.toString();
    }

    public int getID() {
        return id;
    }
}
