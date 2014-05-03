package logic.cards;

import logic.actions.BaseAction;

public class RegularCard {
    String resource;
    int resourceNumber;
    BaseAction action;

    public RegularCard(String resource, int resourceNumber, BaseAction action) {
        this.resource = resource;
        this.resourceNumber = resourceNumber;
        this.action = action;
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
}