package logic.map;

import java.io.Serializable;
import java.util.ArrayList;
import logic.game.Army;
import logic.game.City;
import logic.game.Player;

public abstract class BaseRegion implements Serializable{

    int x;
    int y;
    boolean passable;
    boolean settleable; //Se é possível fundar cidades
    boolean initialRegion;
    ArrayList<City> cities;
    ArrayList<Army> armies;

    public BaseRegion(int y, int x) {
        this.x = x;
        this.y = y;
        this.initialRegion = false;
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

    public boolean isInitialRegion() {
        return initialRegion;
    }

    public Army addArmy(int y, int x, Player player) {
        // jogador escolheu regiao inicial
        if (initialRegion) {
            Army newArmy = new Army(player, this);
            armies.add(newArmy);
            return newArmy;
        }

        // jogador escolheu regiao onde tem uma cidade
        if (player.haveCityInRegion(this)) {
            Army newArmy = new Army(player, this);
            armies.add(newArmy);
            return newArmy;
        }

        return null;
    }

    public Army addArmy(Player player) {
        // Para uso nos 3 exercitos ao inicio
        Army newArmy = new Army(player, this);
        armies.add(newArmy);
        return newArmy;
    }

    public City addCity(int y, int x, Player player) {
        // Jogador escolheu regiao onde tem um exercito E não é no meio do mar
        if (player.haveArmyInRegion(this) && settleable) {
            City newCity = new City(player, this);
            cities.add(newCity);
            return newCity;
        }

        return null;
    }

    public int removeArmy(Player player) {

        for (int i = 0; i < armies.size(); i++) {
            if (armies.get(i).getOwner().equals(player)) {
                armies.remove(i);
                return 0; // Okay
            }
            return 1; // Exército não existe
        }
        return 2; // Erro desconhecido
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

    public void setInicialRegion(boolean b) {
        initialRegion = b;
    }
}
