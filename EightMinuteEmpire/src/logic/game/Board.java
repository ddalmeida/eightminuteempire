package logic.game;

import java.util.ArrayList;
import logic.map.BaseRegion;
import logic.map.Continent;
import logic.map.LandRegion;
import logic.map.WaterRegion;

public class Board {

    private BaseRegion map[][];
    private ArrayList<Continent> continents;

    Board() {
        continents = new ArrayList<Continent>();
        map = new BaseRegion[3][3];

        Continent conti1 = new Continent();
        Continent conti2 = new Continent();

        map[0][0] = new LandRegion(0, 0, true);
        conti1.addRegion(map[0][0]);
        map[0][1] = new LandRegion(0, 1, true);
        conti1.addRegion(map[0][1]);
        map[0][2] = new WaterRegion(0, 2, false);

        map[1][0] = new LandRegion(1, 0, true);
        conti1.addRegion(map[1][1]);
        map[1][1] = new WaterRegion(1, 1, false);
        map[1][2] = new WaterRegion(1, 2, false);

        map[2][0] = new WaterRegion(2, 0, false);
        map[2][1] = new LandRegion(2, 1, false);
        conti2.addRegion(map[2][1]);
        map[2][2] = new LandRegion(2, 2, false);
        conti2.addRegion(map[2][2]);

        continents.add(conti1);
        continents.add(conti2);
    }

    public BaseRegion getRegion(int x, int y) {
        return map[x][y];
    }

    public int getMapSizeY() {
        return map.length;
    }

    public int getMapSizeX() {
        return map[0].length;
    }
}
