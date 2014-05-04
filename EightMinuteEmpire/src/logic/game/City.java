package logic.game;

import logic.map.BaseRegion;

public class City {

    Player owner;
    BaseRegion region;

    public City(Player owner, BaseRegion region) {
        this.owner = owner;
        this.region = region;
    }

    public Player getOwner() {
        return owner;
    }

    public BaseRegion getRegion() {
        return region;
    }
}
