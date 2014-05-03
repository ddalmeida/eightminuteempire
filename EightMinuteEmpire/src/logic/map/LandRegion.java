package logic.map;

public class LandRegion extends BaseRegion {

    public LandRegion(int x, int y, boolean initialRegion) {
        super(x, y, initialRegion);
        passable = true;
        settleable = true;
    }
}