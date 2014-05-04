package logic.map;

public class WaterRegion extends BaseRegion {

    public WaterRegion(int y, int x) {
        super(y, x);
        passable = false;
        settleable = false;
    }
}