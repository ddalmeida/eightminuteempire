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

    public BaseRegion(int y, int x) {
        this.x = x;
        this.y = y;
        this.initialRegion = false;
        cities = new ArrayList<>();
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

    public boolean addArmy(int y, int x, Player player) {
        // jogador escolheu regiao inicial
        // ou jogador escolheu regiao onde tem uma cidade
        if (initialRegion || player.haveCityInRegion(this)) {
            player.addArmy(new Army(this));
            return true;
        }

        return false;
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

    public int countArmiesOfPlayer(Player player) {
        int number = 0;
        for (int i = 0; i < player.getArmies().size(); ++i) {
            if (player.getArmies().get(i).getRegion().equals(this)) {
                number++;
            }
        }
        return number;
    }

    public void setInicialRegion(boolean b) {
        initialRegion = b;
    }
}
