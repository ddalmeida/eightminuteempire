package logic.game;

import java.io.Serializable;
import logic.map.BaseRegion;

public class City implements Serializable{
    BaseRegion region;

    public City(BaseRegion region) {
        this.region = region;
    }

    public BaseRegion getRegion() {
        return region;
    }
}
