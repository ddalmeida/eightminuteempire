package logic.game;

import java.io.Serializable;
import logic.map.BaseRegion;

public class Army implements Serializable{

    Player owner;
    BaseRegion region;

    public Army(Player owner, BaseRegion region) {
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
