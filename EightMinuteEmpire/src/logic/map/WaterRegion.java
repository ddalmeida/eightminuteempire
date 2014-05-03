package logic.map;

public class WaterRegion extends BaseRegion {

    public WaterRegion(int x, int y, boolean initialRegion) {
        super(x, y, initialRegion);
        passable = false;
        settleable = false;
    }
}