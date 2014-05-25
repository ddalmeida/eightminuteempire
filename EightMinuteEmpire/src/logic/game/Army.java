package logic.game;

import java.io.Serializable;
import logic.map.BaseRegion;

public class Army implements Serializable {

    BaseRegion region;

    public Army(BaseRegion region) {
        this.region = region;
    }

    public BaseRegion getRegion() {
        return region;
    }

    public void setRegion(BaseRegion newRegion) {
        region = newRegion;
    }
}
