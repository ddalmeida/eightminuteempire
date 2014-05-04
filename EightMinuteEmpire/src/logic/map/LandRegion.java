package logic.map;

public class LandRegion extends BaseRegion {

    public LandRegion(int y, int x) {
        super(y, x);
        passable = true;
        settleable = true;
    }
}