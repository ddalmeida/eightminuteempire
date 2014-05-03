package logic.map;

import java.util.ArrayList;
import logic.armies.Army;
import logic.cities.City;
import logic.game.Player;

public abstract class BaseRegion {
    int x;
    int y;
    boolean passable;
    boolean settleable; //Se é possível fundar cidades
    boolean initialRegion;
    ArrayList<City> cities;
    ArrayList<Army> armies;

    public BaseRegion(int x, int y, boolean initialRegion) {
        this.x = x;
        this.y = y;
        this.initialRegion = initialRegion;
        cities = new ArrayList<>();
        armies = new ArrayList<>();
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

    // ***************************************************IMPORTANTE*****************************************************
    public int addArmy(Player player, Army army) {
        if (initialRegion) {
            armies.add(army);
            return 0;
        } else if (passable) {
            for (int i = 0; i < cities.size(); i++) {
                //  ********WTF não me deixa aceder aos métodos da classe City**********************************
                if (cities.get(i).getOwner() == player) {
                    armies.add(army);
                } else {
                    return 1; // 1 = não possui cidade nesta região
                }
            }
        } else {
            return 2; // 2 = não é possível colocar exércitos nesta região
        }
        return 3; // 3 = erro desconhecido
    }
    public int addCity(Player player, City city) {
        if (settleable) {
            for (int i = 0; i < armies.size(); i++) {
                if (armies.get(i).getOwner() == player) {
                    for (int j = 0; j < cities.size(); j++) {
                        //  ********WTF não me deixa aceder aos métodos da classe City******************
                        if (cities.get(j).getOwner() == player) {
                            return 1; // 1 = já possui uma cidade aqui
                        } else {
                            return 0; // 0 = Okay
                        }
                    }
                } else {
                    return 3; // 3 = não possui exércitos aqui
                }
            }
        } else {
            return 2; // 2 = não é possível fundar cidade - água
        }
        return 4; // 4 = erro desconhecido
    }

    public int countArmies(Player player) {
        int number = 0;
        for (int i = 0; i < armies.size(); i++) {
            if (armies.get(i).getOwner() == player) {
                number++;
            }
        }
        return number;
    }
}