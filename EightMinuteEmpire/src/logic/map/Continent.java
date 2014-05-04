package logic.map;

import java.util.ArrayList;

public class Continent {

    private ArrayList<BaseRegion> regions;

    public Continent() {
        regions = new ArrayList();
    }

    public void addRegion(BaseRegion region) {
        regions.add(region);
    }
}
