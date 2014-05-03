package logic.cards;

import logic.actions.BaseAction;

public class AndCard extends RegularCard{
    private BaseAction action2;

    public AndCard(String resource, int resourceNumber, BaseAction action, BaseAction action2) {
        super(resource, resourceNumber, action);
        this.action2 = action2;
    }
}
