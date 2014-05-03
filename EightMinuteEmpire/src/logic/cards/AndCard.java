package logic.cards;

import logic.actions.BaseAction;

public class AndCard extends RegularCard{
    private BaseAction action2;

    public AndCard(String resource, int resourceNumber, BaseAction action, BaseAction action2) {
        super(resource, resourceNumber, action);
        this.action2 = action2;
    }
    
    public String toString()
    {
        return resource + " card with " + action.toString() + " AND " + action2.toString();
    }
    
       public BaseAction getAction2() {
        return action2;
    }
}
