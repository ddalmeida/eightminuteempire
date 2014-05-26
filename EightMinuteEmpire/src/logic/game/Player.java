package logic.game;

import java.io.Serializable;
import java.util.ArrayList;
import logic.cards.RegularCard;
import logic.map.BaseRegion;

public class Player implements Comparable<Player>, Serializable {

    private String name;
    private int coins;
    private int points;
    private int initialBet;
    private ArrayList<RegularCard> cardsInHand;
    private ArrayList<Army> armies;
    private ArrayList<City> cities;

    Player(String name, int coins) {
        this.name = name;
        this.coins = coins;
        points = 0;
        initialBet = 0;
        cardsInHand = new ArrayList<>();
        armies = new ArrayList<>();
        cities = new ArrayList<>();
    }

    @Override
    public int compareTo(Player o) {
        return (int) (o.getPoints() - this.points);
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public int getInitialBet() {
        return initialBet;
    }

    public void setInitialBet(int initialBet) {
        this.initialBet = initialBet;
    }

    public void removeCoins(int n) {
        coins -= n >= 0 ? n : 0;
    }

    public void addCoins(int n) {
        coins += n;
    }

    // inutil?
    public void addPoints(int n) {
        points += n;
    }

    // inutil?
    public int getPoints() {
        return points;
    }

    public void addCard(RegularCard Card) {
        cardsInHand.add(Card);
    }

    public ArrayList<RegularCard> getCardsInHand() {
        return cardsInHand;
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public ArrayList<City> getCities() {
        return cities;
    }
    
    public int addCity(BaseRegion region) {
        if (cities.size() >= 3) return 2;
        
        // Jogador escolheu regiao onde tem um exercito E não é no meio do mar
        if (haveArmyInRegion(region) && region.isSettleable()) {
            cities.add(new City(region));
            return 1;
        }

        return 0;
    }


    public ArrayList<Army> getArmies() {
        return armies;
    }

    public int addArmy(BaseRegion region) {
        if (armies.size() >= 14) {
            return 2; // nao pode ter mais exercitos
        }

        // jogador escolheu regiao inicial
        // ou jogador escolheu regiao onde tem uma cidade
        if (region.isInitialRegion() || haveCityInRegion(region)) {
            armies.add(new Army(region));
            return 1; // novo exercito adicionado
        }
        return 0; // O jogador não escolheu a regiao inicial ou uma regiao com cidade
    }

    public boolean removeArmy(BaseRegion region) {

        for (int i = 0; i < armies.size(); i++) {
            if (armies.get(i).getRegion().equals(region)) {
                armies.remove(i);
                return true; // Apaga o primeiro exercito encontrado na regiao
            }
        }
        return false; // Nenhum exercito encontrado na regiao
    }

    public boolean haveArmyInRegion(BaseRegion region) {
        for (int i = 0; i < armies.size(); ++i) {
            if (armies.get(i).getRegion().equals(region)) {
                return true;
            }
        }

        return false;
    }

    public boolean haveCityInRegion(BaseRegion region) {
        for (int i = 0; i < armies.size(); ++i) {
            if (cities.get(i).getRegion() == region) {
                return true;
            }
        }

        return false;
    }
}
