package logic.map;

import java.io.Serializable;
import java.util.ArrayList;
import logic.game.Army;
import logic.game.City;
import logic.game.Player;

public abstract class BaseRegion implements Serializable {

      protected int x;
      protected int y;
      protected boolean passable;
      protected boolean settleable; //Se é possível fundar cidades
      protected boolean initialRegion;

      public BaseRegion(int y, int x) {
            this.x = x;
            this.y = y;
            this.initialRegion = false;
      }

      public int getX() {
            return x;
      }

      public int getY() {
            return y;
      }

      public boolean isPassable() {
            return passable;
      }

      public boolean isSettleable() {
            return settleable;
      }

      public boolean isInitialRegion() {
            return initialRegion;
      }

          public int countArmiesOfPlayer(Player player) {
            int number = 0;
            for (int i = 0; i < player.getArmies().size(); ++i) {
                  if (player.getArmies().get(i).getRegion().equals(this)) {
                        number++;
                  }
            }
            return number;
      }

      // Devolve os exercitos do jogador X presentes na região
      public ArrayList<Army> armiesOfPlayer(Player player) {
            ArrayList<Army> armies = new ArrayList<>();

            for (int i = 0; i < player.getArmies().size(); ++i) {
                  if (player.getArmies().get(i).getRegion().equals(this)) {
                        armies.add(player.getArmies().get(i));
                  }
            }

            return armies;
      }

      // Devolve as cidades do jogador X presentes na região
      public ArrayList<City> citiesOfPlayer(Player player) {
            ArrayList<City> cities = new ArrayList<>();

            for (int i = 0; i < player.getCities().size(); ++i) {
                  if (player.getCities().get(i).getRegion().equals(this)) {
                        cities.add(player.getCities().get(i));
                  }
            }

            return cities;
      }

      public void setInicialRegion(boolean b) {
            initialRegion = b;
      }
}
