package logic.map;

import java.io.Serializable;
import java.util.ArrayList;

public class Continent implements Serializable {

      private ArrayList<BaseRegion> regions;

      public Continent() {
            regions = new ArrayList<>();
      }

      public void addRegion(BaseRegion region) {
            regions.add(region);
      }

      public ArrayList<BaseRegion> getRegions() {
            return regions;
      }
}
