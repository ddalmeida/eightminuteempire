package logic.cards;

import logic.actions.BaseAction;

public class OrCard extends RegularCard{
    private BaseAction action2;

    public OrCard(String resource, int resourceNumber, BaseAction action, BaseAction action2) {
        super(resource, resourceNumber, action);
        this.action2 = action2;
    }
}
