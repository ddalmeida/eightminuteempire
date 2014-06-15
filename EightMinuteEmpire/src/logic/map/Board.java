package logic.map;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

      protected BaseRegion map[][];
      protected ArrayList<Continent> continents;
      protected ArrayList<SeaPath> seapaths;

      protected Board() {
      }

      public BaseRegion getRegion(int x, int y) {
            if (x < 0 || y < 0 || x > getMapSizeX() - 1 || y > getMapSizeY() - 1) {
                  return null;
            } else {
                  return map[x][y];
            }
      }

      public int getMapSizeY() {
            return map.length;
      }

      public int getMapSizeX() {
            return map[0].length;
      }

      public BaseRegion getInicialRegion() {
            for (int i = 0; i < map.length; ++i) {
                  for (int j = 0; j < map[0].length; ++j) {
                        if (map[i][j].isInitialRegion()) {
                              return map[i][j];
                        }
                  }
            }

            // Nao há região inicial???
            return null;
      }

      public boolean isThereSeaPath(BaseRegion lr1, BaseRegion lr2) {
            for (int i = 0; i < seapaths.size(); ++i) {

                  if ((seapaths.get(i).getRegion1() == lr1 && seapaths.get(i).getRegion2() == lr2)
                          || seapaths.get(i).getRegion1() == lr2 && seapaths.get(i).getRegion2() == lr1) {
                        return true;
                  }
            }

            return false;
      }

      public boolean areAdjacent(BaseRegion b1, BaseRegion b2, boolean overSea) {
            // Se uma região é adejacente, o valor da conta tem de dar 1 (diagonais ignoradas).
            // Se der 0 é porque a região é a mesma (ou algo está muito mal).
            if (Math.abs(b1.getX() + b2.getX()) + Math.abs(b1.getY() + b2.getY()) <= 1) {
                  return true;
            }

            // Se é a conta deu 2 ou mais, ver se tem caminho maritimo para lá
            if (overSea) {
                  return isThereSeaPath(b1, b2);
            }

            return false;
      }

      public ArrayList<Continent> getContinents() {
            return continents;
      }
}
