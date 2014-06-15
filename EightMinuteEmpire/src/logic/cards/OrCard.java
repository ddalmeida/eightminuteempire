package logic.cards;

import logic.actions.BaseAction;

public class OrCard extends RegularCard {

      private BaseAction action2;

      public OrCard(Type resource, int resourceNumber, BaseAction action, BaseAction action2, int imageID) {
            super(resource, resourceNumber, action, imageID);
            this.action2 = action2;
      }

      @Override
      public BaseAction getAction2() {
            return action2;
      }

      @Override
      public String toString() {
            return resource.toString() + " (" + resourceNumber + ") card with " + action.toString() + " OR " + action2.toString();
      }
}
