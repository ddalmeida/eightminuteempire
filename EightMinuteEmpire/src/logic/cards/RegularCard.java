package logic.cards;

import java.io.Serializable;
import logic.actions.BaseAction;

public class RegularCard implements Serializable {

      public static enum Type {

            Jewels(0), Food(1), Wood(2), Iron(3), Tools(4), Joker(5);

            private final int code;

            private Type(int c) {
                  code = c;
            }

            public int getCode() {
                  return code;
            }
      }

     Type resource;
      int resourceNumber;
      BaseAction action;
      //static int idc = 0;
      int imageID;

      public RegularCard(Type resource, int resourceNumber, BaseAction action, int imageID) {
            this.resource = resource;
            this.resourceNumber = resourceNumber;
            this.action = action;
            //id = idc++;
            this.imageID = imageID;
      }

      public Type getResource() {
            return resource;
      }
      
      public void setResource(Type newType) {
            resource = newType;
      }

      public int getResourceNumber() {
            return resourceNumber;
      }

      public BaseAction getAction() {
            return action;
      }

      public BaseAction getAction2() {
            return null;
      }

      @Override
      public String toString() {
            return resource.toString() + " (" + resourceNumber + ") card with " + action.toString();
      }

      public int getImageID() {
            return imageID;
      }
}
