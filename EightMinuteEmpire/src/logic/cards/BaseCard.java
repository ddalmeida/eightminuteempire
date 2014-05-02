package logic.cards;

public class BaseCard {
    String resource;
    int resourceNumber;
    String action;

    public BaseCard(String resource, int resourceNumber, String action) {
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

    public String getAction() {
        return action;
    }
}