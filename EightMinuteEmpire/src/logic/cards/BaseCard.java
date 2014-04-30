package logic.cards;

import logic.resources.*;
import logic.actions.*;

public abstract class BaseCard {
    BaseResource resource;
    int resourceNumber;
    BaseAction action;

    public BaseResource getResource() {
        return resource;
    }

    public int getResourceNumber() {
        return resourceNumber;
    }

    public BaseAction getAction() {
        return action;
    }
}