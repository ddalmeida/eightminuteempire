package logic.game;

// Tabuleiro simples para jogo em modo consola
import logic.map.Board;
import java.util.ArrayList;
import logic.map.BaseRegion;
import logic.map.Continent;
import logic.map.LandRegion;
import logic.map.SeaPath;
import logic.map.WaterRegion;

public class Board_Console extends Board {

      public Board_Console()
      {
            continents = new ArrayList<>();
            seapaths = new ArrayList<>();
            map = new BaseRegion[3][3];

            Continent conti1 = new Continent();
            Continent conti2 = new Continent();

            map[0][0] = new LandRegion(0, 0);
            conti1.addRegion(map[0][0]);
            map[0][1] = new LandRegion(0, 1);
            conti1.addRegion(map[0][1]);
            map[0][2] = new WaterRegion(0, 2);

            map[1][0] = new LandRegion(1, 0);
            conti1.addRegion(map[1][1]);
            map[1][1] = new WaterRegion(1, 1);
            map[1][2] = new WaterRegion(1, 2);

            map[2][0] = new WaterRegion(2, 0);
            map[2][1] = new LandRegion(2, 1);
            conti2.addRegion(map[2][1]);
            map[2][2] = new LandRegion(2, 2);
            conti2.addRegion(map[2][2]);

            continents.add(conti1);
            continents.add(conti2);

            map[0][1].setInicialRegion(true);

            seapaths.add(new SeaPath(map[1][0], map[2][1]));
      }
}
